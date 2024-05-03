package com.ssafy.challs.domain.contest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssafy.challs.domain.contest.dto.request.ContestCreateRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestUpdateRequestDto;
import com.ssafy.challs.domain.contest.dto.response.ContestAwardsDto;
import com.ssafy.challs.domain.contest.entity.Awards;
import com.ssafy.challs.domain.contest.entity.Contest;
import com.ssafy.challs.domain.team.entity.Team;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContestMapper {

	@Mapping(source = "team", target = "team")
	@Mapping(source = "contestRequestDto.registrationPeriod.start", target = "contestRegistrationStart")
	@Mapping(source = "contestRequestDto.registrationPeriod.end", target = "contestRegistrationEnd")
	@Mapping(source = "contestRequestDto.contestPeriod.start", target = "contestStart")
	@Mapping(source = "contestRequestDto.contestPeriod.end", target = "contestEnd")
	Contest contestCreateDtoToContest(ContestCreateRequestDto contestRequestDto, Team team, Character contestState);

	@Mapping(source = "contest", target = "contest")
	@Mapping(target = "id", ignore = true)
	Awards awardsDtoToEntity(ContestAwardsDto contestAwardsDto, Contest contest);

	@Mapping(source = "contestRequestDto.contestId", target = "id")
	@Mapping(source = "team", target = "team")
	@Mapping(source = "contestRequestDto.registrationPeriod.start", target = "contestRegistrationStart")
	@Mapping(source = "contestRequestDto.registrationPeriod.end", target = "contestRegistrationEnd")
	@Mapping(source = "contestRequestDto.contestPeriod.start", target = "contestStart")
	@Mapping(source = "contestRequestDto.contestPeriod.end", target = "contestEnd")
	Contest contestUpdateDtoToContest(ContestUpdateRequestDto contestRequestDto, Team team, Character contestState);
}
