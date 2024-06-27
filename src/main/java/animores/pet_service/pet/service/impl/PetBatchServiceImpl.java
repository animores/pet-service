package animores.pet_service.pet.service.impl;

import animores.pet_service.account.repository.AccountRepository;
import animores.pet_service.pet.entity.Pet;
import animores.pet_service.pet.repository.BreedRepository;
import animores.pet_service.pet.repository.PetImageRepository;
import animores.pet_service.pet.service.PetBatchService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetBatchServiceImpl implements PetBatchService {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final AccountRepository accountRepository;
    private final BreedRepository breedRepository;
    private final PetImageRepository petImageRepository;

    @Override
    public void insertPetBatch(Integer count, Integer accountStartId) {
        try{
            jobLauncher.run(
                    new JobBuilder("petBatchInsertJob", jobRepository)
                            .incrementer(new RunIdIncrementer())
                            .start(petBatchInsertStep(count, accountStartId))
                            .build()
                    , new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    private Step petBatchInsertStep(Integer count, Integer accountStartId) {
        return new StepBuilder("petBatchInsertStep", jobRepository)
                .<Pet, Pet>chunk(100, transactionManager)
                .reader(new PetBatchInsertFactory(count, accountStartId, accountRepository, breedRepository, petImageRepository))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private static class PetBatchInsertFactory implements ItemReader<Pet> {
        private int currentIdx = 0;
        private final int accountStartId;
        private final int count;
        private final AccountRepository accountRepository;
        private final BreedRepository breedRepository;
        private final PetImageRepository petImageRepository;

        public PetBatchInsertFactory(Integer count,
                                     Integer accountStartId,
                                     AccountRepository accountRepository,
                                     BreedRepository breedRepository,
                                     PetImageRepository petImageRepository) {
            this.count = count;
            this.accountRepository = accountRepository;
            this.accountStartId = accountStartId;
            this.breedRepository = breedRepository;
            this.petImageRepository = petImageRepository;
        }

        @Override
        public Pet read() throws Exception {
            if (currentIdx < count) {
                currentIdx++;
                String randomString = UUID.randomUUID().toString();
                return Pet.builder()
                        .name(randomString.substring(0,10))
                        .account(accountRepository.getReferenceById((long)(currentIdx/3 + accountStartId)))
                        .breed(breedRepository.getReferenceById(1L))
                        .birthday(LocalDate.of(2021, 1, 1))
                        .image(petImageRepository.getReferenceById(1L))
                        .gender(currentIdx % 2)
                        .build();
            } else {
                return null;
            }
        }
    }

    private ItemProcessor<Pet, Pet> itemProcessor() {
        return item -> item;
    }

    private JpaItemWriter<Pet> itemWriter() {
        JpaItemWriter<Pet> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

}
