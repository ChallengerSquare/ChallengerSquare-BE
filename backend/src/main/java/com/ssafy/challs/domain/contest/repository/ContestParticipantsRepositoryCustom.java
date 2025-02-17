package com.ssafy.challs.domain.contest.repository;

import java.util.List;

import com.ssafy.challs.domain.contest.dto.ContestParticipantsInfoDto;
import com.ssafy.challs.domain.contest.dto.ContestParticipantsLeaderStateDto;
import com.ssafy.challs.domain.contest.dto.ContestTeamInfoDto;
import com.ssafy.challs.domain.contest.dto.response.ContestTeamMemberInfoDto;
import com.ssafy.challs.domain.contest.entity.ContestParticipants;

public interface ContestParticipantsRepositoryCustom {

	boolean checkAlreadyParticipantsMember(Long contestId, Long teamId);

	ContestParticipants findContestParticipants(Long contestId, Long memberId);

	List<ContestTeamInfoDto> searchTeamInfoByContest(Long contestId, Character state);

	List<ContestTeamMemberInfoDto> searchTeamMemberByTeamId(Long teamId);

	void updateContestParticipantsState(Long contestId, List<Long> agreeMembers);

	List<ContestParticipantsInfoDto> findAllTeamFromContestId(Long contestId);

	List<Long> searchMemberIdFromTeamId(Long teamId);

	ContestParticipantsLeaderStateDto isLeaderAndParticipantsState(Long contestId, Long memberId);

	void updateContestIsParticipants(Long contestId, List<Long> participantsTeams);

	List<Long> searchMemberIdListFromContestId(Long contestId);
}
