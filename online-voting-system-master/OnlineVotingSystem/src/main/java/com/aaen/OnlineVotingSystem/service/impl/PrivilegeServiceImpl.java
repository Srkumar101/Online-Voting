package com.aaen.OnlineVotingSystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aaen.OnlineVotingSystem.model.PrivilegeRequestResponse;
import com.aaen.OnlineVotingSystem.repository.PrivilegeRepository;
import com.aaen.OnlineVotingSystem.service.PrivilegeService;
import com.aaen.OnlineVotingSystem.util.Constant;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeRepository repository;

	@Override
	public String createPrivilege(PrivilegeRequestResponse privilege) {

		try {

			privilege.setId(UUID.randomUUID().toString());

			repository.save(privilege);

			return Constant.CREATED;

		} catch (Exception e) {

			return e.toString();
		}
	}

	@Override
	public PrivilegeRequestResponse getPrivilegeById(String id) {

		Optional<PrivilegeRequestResponse> optional = repository.findById(id);

		if (optional.isPresent())
			return optional.get();

		return null;
	}

	@Override
	public List<PrivilegeRequestResponse> getPrivileges() {

		List<PrivilegeRequestResponse> privileges = repository.findAll();

		privileges.sort((PrivilegeRequestResponse p1, PrivilegeRequestResponse p2) -> p1.getPrivilege()
				.compareTo(p2.getPrivilege()));

		return privileges;
	}

	@Override
	public HttpStatus deletePrivilege(String id) {

		repository.deleteById(id);

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus deletePrivileges() {

		repository.deleteAll();

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public String updatePrivilege(PrivilegeRequestResponse privilege) {
		if (privilege.getId() != null
				&& (privilege.getAccessibleForUi() != null || privilege.getAccessibleForUser() != null
						|| privilege.getActive() != null || privilege.getDescription() != null
						|| privilege.getParent() != null || privilege.getPrivilege() != null)) {

			PrivilegeRequestResponse dbPrivilege = getPrivilegeById(privilege.getId());

			if (privilege.getPrivilege() != null)
				dbPrivilege.setPrivilege(privilege.getPrivilege());

			if (privilege.getActive() != null)
				dbPrivilege.setActive(privilege.getActive());

			if (privilege.getAccessibleForUi() != null)
				dbPrivilege.setAccessibleForUi(privilege.getAccessibleForUi());

			if (privilege.getDescription() != null)
				dbPrivilege.setDescription(privilege.getDescription());

			if (privilege.getParent() != null)
				dbPrivilege.setParent(privilege.getParent());

			if (privilege.getAccessibleForUser() != null)
				dbPrivilege.setAccessibleForUser(privilege.getAccessibleForUser());

			repository.save(dbPrivilege);

			return Constant.UPDATED;
		}

		return Constant.RECORD_NOT_FOUND;
	}

}
