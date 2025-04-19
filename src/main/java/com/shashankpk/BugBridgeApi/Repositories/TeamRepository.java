package com.shashankpk.BugBridgeApi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shashankpk.BugBridgeApi.Model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
	
}
