package member.controller;

import java.util.List;

import member.model.exception.MemberException;
import member.model.service.MemberService;
import member.model.vo.Member;
import member.view.MemberMenu;

public class MemberController {

	private MemberService memberService = new MemberService();

	public List<Member> selectAll() {
		List<Member> list = null;
		try {
			list = memberService.selectAll();
		} catch(MemberException e) {
			//서버로깅
			//관리자 이메일 알림
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}
	
	public int insertMember(Member m) {
		return memberService.insertMember(m);
	}

	public Member selectOne(String memberId) {
		return memberService.selectOneMember(memberId); 
	}

	public int deleteMember(String memberId) {
		return memberService.deleteMember(memberId);
	}
	
	public int updateMember(Member m) {
		return memberService.updateMember(m);
	}
	
	public List<Member> selectByName(String memberName) {
		return memberService.selectByName(memberName);
	}
		
}
