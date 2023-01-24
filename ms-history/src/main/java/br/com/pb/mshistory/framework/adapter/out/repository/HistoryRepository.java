package br.com.pb.mshistory.framework.adapter.out.repository;

import br.com.pb.mshistory.domain.dto.HistoryDTO;
import br.com.pb.mshistory.domain.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends MongoRepository<History, UUID> {

    History save(History history);

}
