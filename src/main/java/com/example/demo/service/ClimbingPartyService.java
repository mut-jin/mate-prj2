package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class ClimbingPartyService {

	@Autowired
	private ClimbingPartyMapper partyMapper;

	@Autowired
	private ClimbingMateMapper mateMapper;

	
	public Map<String, Object> join(ClimbingParty climbingParty, Authentication authentication) {

		Member member = partyMapper.selectMemberById(authentication.getName());
		

		ClimbingMate board = mateMapper.selectById(climbingParty.getBoardId());
		int currentNum = partyMapper.countByBoardId(climbingParty.getBoardId());

		Map<String, Object> result = new HashMap<>();

		System.out.println(board.getPeople() + "총인원");
		System.out.println(currentNum + "현재인원");

		if (board.getPeople() > currentNum) {

			climbingParty.setMemberId(member.getNickName());

			System.out.println(climbingParty);

			result.put("join", false);

			Integer deleteCnt = partyMapper.delete(climbingParty);

			if (deleteCnt != 1) {
				Integer insertCnt = partyMapper.insert(climbingParty);
				result.put("join", true);
			}

			// 참여인원갯수 넘겨주기
			Integer count = partyMapper.countByBoardId(climbingParty.getBoardId());

			result.put("count", count);
			System.out.println("****" + result);
			return result;

		} else {
			// 신청 불가능한 경우
			result.put("join", false);
			result.put("message", "신청이 불가능합니다."); // 메시지 추가
			System.out.println(result);
			return result;
		}
		
		

	}

	public Map<String, Object> reject(ClimbingParty climbingParty, Authentication authentication) {

		climbingParty.setMemberId(authentication.getName());
		System.out.println(climbingParty.getMemberId());

		Map<String, Object> result = new HashMap<>();

		result.put("join", false);

		Integer deleteCnt = partyMapper.delete(climbingParty);
		System.out.println(deleteCnt);

		// 참여인원갯수 넘겨주기
		Integer count = partyMapper.countByBoardId(climbingParty.getBoardId());

		System.out.println(climbingParty.getBoardId());
		System.out.println(count);
		result.put("count", count);

		return result;
	}

}