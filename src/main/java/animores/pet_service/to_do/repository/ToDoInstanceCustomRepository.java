package animores.pet_service.to_do.repository;


import animores.pet_service.to_do.dao.GetToDoPageDao;

import java.util.List;

public interface ToDoInstanceCustomRepository {

	GetToDoPageDao findAllByCompleteFalseAndTodayToDoIdIn(List<Long> toDoIds, Integer page, Integer size);
	GetToDoPageDao findAllByCompleteAndTodayToDoIdIn(List<Long> toDoIds, Integer page, Integer size);
	GetToDoPageDao findAllByCompleteFalseAndToDoIdIn(List<Long> toDoIds, Integer page, Integer size);
	GetToDoPageDao findAllByCompleteAndToDoIdIn(List<Long> toDoIds, Integer page, Integer size);
	GetToDoPageDao findAllByTodayToDoIdIn(List<Long> todoIdList, Integer page, Integer size);
	GetToDoPageDao findAllByToDoIdIn(List<Long> todoIdList, Integer page, Integer size);
}
