package com.ssafy.challs.domain.member.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.challs.domain.auth.jwt.dto.TokenCookie;
import com.ssafy.challs.domain.auth.jwt.repository.RefreshTokenRepository;
import com.ssafy.challs.domain.auth.jwt.service.CookieUtil;
import com.ssafy.challs.domain.auth.jwt.service.TokenProvider;
import com.ssafy.challs.domain.contest.repository.ContestRepository;
import com.ssafy.challs.domain.member.dto.request.MemberCreateRequestDto;
import com.ssafy.challs.domain.member.dto.request.MemberUpdateRequestDto;
import com.ssafy.challs.domain.member.dto.response.MemberAwardsCodeResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberContestResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberFindResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberTeamLeaderResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberTeamResponseDto;
import com.ssafy.challs.domain.member.repository.AwardsCodeRepository;
import com.ssafy.challs.domain.member.repository.MemberRepository;
import com.ssafy.challs.domain.member.service.MemberService;
import com.ssafy.challs.domain.team.repository.TeamParticipantsRepository;
import com.ssafy.challs.domain.team.repository.TeamRepository;
import com.ssafy.challs.global.common.exception.BaseException;
import com.ssafy.challs.global.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenProvider tokenProvider;
	private final CookieUtil cookieUtil;
	private final TeamRepository teamRepository;
	private final ContestRepository contestRepository;
	private final AwardsCodeRepository awardsCodeRepository;
	private final TeamParticipantsRepository teamParticipantsRepository;

	/**
	 * refresh token을 기준으로 새로운 accessToken과 refreshToken 발급
	 * @author 강태연
	 * @param refreshToken refreshToken
	 * @return 토큰을 저장할 HttpHeaders
	 */
	@Override
	@Transactional
	public HttpHeaders createToken(String refreshToken) {
		if (!tokenProvider.validationToken(refreshToken)) {
			throw new BaseException(ErrorCode.WRONG_TOKEN_ERROR);
		}
		String idFromToken = tokenProvider.getIdFromToken(refreshToken);
		String memberId = refreshTokenRepository.getAndRemove(idFromToken);
		if (memberId == null) {
			throw new BaseException(ErrorCode.WRONG_TOKEN_ERROR);
		}
		TokenCookie tokenCookie = tokenProvider.makeTokenCookie(memberId);
		return getHeaders(tokenCookie);
	}

	/**
	 * refresh token을 redis에서 삭제하고 cookie를 삭제
	 * @author 강태연
	 * @param refreshToken refreshToken
	 * @return 토큰을 저장할 HttpHeaders
	 */
	@Override
	@Transactional
	public HttpHeaders deleteRefreshToken(String refreshToken) {
		if (!tokenProvider.validationToken(refreshToken)) {
			throw new BaseException(ErrorCode.WRONG_TOKEN_ERROR);
		}
		String idFromToken = tokenProvider.getIdFromToken(refreshToken);
		refreshTokenRepository.remove(idFromToken);
		TokenCookie tokenCookie = cookieUtil.deleteCookie();
		return getHeaders(tokenCookie);
	}

	// 생성한 쿠키를 저장할 headers 생성
	private HttpHeaders getHeaders(TokenCookie tokenCookie) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, tokenCookie.accessCookie().toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, tokenCookie.refreshCookie().toString());
		return httpHeaders;
	}

	/**
	 * 회원가입
	 * @author 강태연
	 * @param memberCreateRequestDto 회원가입에 필요한 정보
	 * @param memberId 회원 pk
	 */
	@Override
	@Transactional
	public void createMember(MemberCreateRequestDto memberCreateRequestDto, Long memberId) {
		memberRepository.createMember(memberCreateRequestDto, memberId);
	}

	/**
	 * 정보수정
	 * @author 강태연
	 * @param memberUpdateRequestDto 정보수정에 필요한 정보
	 * @param memberId 회원 pk
	 */
	@Override
	@Transactional
	public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto, Long memberId) {
		memberRepository.updateMember(memberUpdateRequestDto, memberId);
	}

	/**
	 * 정보 조회
	 * @author 강태연
	 * @param memberId 회원 pk
	 * @return 회원 조회 결과
	 */
	@Override
	@Transactional(readOnly = true)
	public MemberFindResponseDto findMember(Long memberId) {
		return memberRepository.findMember(memberId).orElseThrow(() -> new BaseException(ErrorCode.MEMBER_FOUND_ERROR));
	}

	/**
	 * 회원 탈퇴
	 * @author 강태연
	 * @param memberId 회원 pk
	 */
	@Override
	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteMember(memberId);
	}

	/**
	 * 가입된 팀 목록 조회
	 *
	 * @author 강태연
	 * @param memberId 현재 요청 보낸 멤버 정보
	 * @param pageable 페이징 정보
	 * @return 팀 정보 목록
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MemberTeamResponseDto> searchTeamList(Long memberId, Pageable pageable) {
		return teamRepository.searchTeamList(memberId, pageable);
	}

	/**
	 * 멤버가 가입된 모든 팀이 대회 참가 대기, 대회 참가 승인 상태인 대회(대회 모집 시작, 대회 모집 완료, 대회 시작, 대회 끝)의 목록 조회
	 *
	 * @author 강태연
	 * @param pageable 페이징 정보
	 * @param memberId 현재 요청 보낸 멤버 정보
	 * @return 팀 정보 목록
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MemberContestResponseDto> searchContestList(Pageable pageable, Long memberId) {
		return contestRepository.searchContestList(pageable, memberId);
	}

	/**
	 * 시상 정보 목록 조회
	 *
	 * @author 강태연
	 * @param memberId 현재 요청 보낸 멤버 정보
	 * @param pageable 페이징 정보
	 * @return 시상 정보 목록
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MemberAwardsCodeResponseDto> searchAwardList(Long memberId, Pageable pageable) {
		return awardsCodeRepository.searchAwardList(pageable, memberId);
	}

	/**
	 * 멤버이 가입한 팀 중에서 리더인 팀의 목록을 조회
	 *
	 * @author 강태연
	 * @param memberId 현재 요청 보낸 멤버 정보
	 * @return 팀 정보 목록
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MemberTeamLeaderResponseDto> searchTeamLeaderList(Long memberId) {
		return teamParticipantsRepository.searchTeamLeaderList(memberId);
	}

}
