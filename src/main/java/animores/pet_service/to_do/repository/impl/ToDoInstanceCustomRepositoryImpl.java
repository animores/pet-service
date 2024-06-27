package animores.pet_service.to_do.repository.impl;


import animores.pet_service.profile.dao.ProfileVo;
import animores.pet_service.to_do.dao.GetToDoPageDao;
import animores.pet_service.to_do.dao.ToDoDao;
import animores.pet_service.to_do.dao.ToDoInstanceDao;
import animores.pet_service.to_do.repository.ToDoInstanceCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static animores.pet_service.profile.entity.QProfile.profile;
import static animores.pet_service.to_do.entity.QToDo.toDo;
import static animores.pet_service.to_do.entity.QToDoInstance.toDoInstance;


@Repository
@RequiredArgsConstructor
public class ToDoInstanceCustomRepositoryImpl implements ToDoInstanceCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;


	@Override
	public GetToDoPageDao findAllByCompleteFalseAndTodayToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {
		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds)
						.and(toDoInstance.date.eq(LocalDate.now()))
						.and(toDoInstance.completeProfile.isNull()))
				.fetch()
				.size();

		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds)
								.and(toDoInstance.date.eq(LocalDate.now()))
								.and(profile.isNull()))
						.offset(offset)
						.limit(size)
						.fetch());

	}

	@Override
	public GetToDoPageDao findAllByCompleteAndTodayToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {

		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds)
						.and(toDoInstance.date.eq(LocalDate.now()))
						.and(toDoInstance.completeProfile.isNotNull()))
				.fetch()
				.size();

		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds)
								.and(toDoInstance.date.eq(LocalDate.now()))
								.and(profile.isNotNull())
						)
						.offset(offset)
						.limit(size)
						.fetch()
		);
	}

	@Override
	public GetToDoPageDao findAllByTodayToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {
		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds)
						.and(toDoInstance.date.eq(LocalDate.now())))
				.fetch()
				.size();


		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds)
								.and(toDoInstance.date.eq(LocalDate.now())))
						.offset(offset)
						.limit(size)
						.fetch()
		);
	}

	@Override
	public GetToDoPageDao findAllByCompleteFalseAndToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {

		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds)
						.and(toDoInstance.completeProfile.isNull()))
				.fetch()
				.size();

		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds)
								.and(profile.isNull()))
						.offset(offset)
						.limit(size)
						.fetch()
		);
	}

	@Override
	public GetToDoPageDao findAllByCompleteAndToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {
		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds)
						.and(toDoInstance.completeProfile.isNotNull()))
				.fetch()
				.size();

		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds)
								.and(profile.isNotNull()))
						.offset(offset)
						.limit(size)
						.fetch()
		);
	}

	@Override
	public GetToDoPageDao findAllByToDoIdIn(List<Long> toDoIds, Integer page, Integer size) {
		int offset = (page - 1) * size;
		int totalCount = jpaQueryFactory.select(toDoInstance.id)
				.from(toDoInstance)
				.join(toDoInstance.toDo, toDo)
				.where(toDo.id.in(toDoIds))
				.fetch()
				.size();

		return new GetToDoPageDao(
				page,
				size,
				totalCount,
				totalCount % size == 0 ? totalCount/size : totalCount/size + 1,
				jpaQueryFactory.select(
								Projections.constructor(
										ToDoInstanceDao.class,
										toDoInstance.id,
										Projections.constructor(
												ToDoDao.class,
												toDo.id,
												toDo.isAllDay,
												toDo.content,
												toDo.tag,
												toDo.color,
												toDo.isUsingAlarm
										),
										toDoInstance.date.min().as("date"),
										toDoInstance.time.min().as("time"),
										Projections.constructor(
												ProfileVo.class,
												profile.name,
												profile.imageUrl),
										toDoInstance.completeTime
								)
						)
						.from(toDoInstance)
						.join(toDoInstance.toDo, toDo)
						.leftJoin(toDoInstance.completeProfile, profile)
						.groupBy(toDo)
						.where(toDo.id.in(toDoIds))
						.offset(offset)
						.limit(size)
						.fetch()
		);
	}
}
