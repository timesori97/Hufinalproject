package www.silver.hom; // 패키지 선언

import org.springframework.stereotype.Controller; // 컨트롤러 어노테이션
import org.springframework.ui.Model; // 모델 객체
import org.springframework.web.bind.annotation.*; // 웹 요청 어노테이션
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 리다이렉트 속성

import www.silver.service.ProblemService; // ProblemService 인터페이스
import www.silver.service.SubmissionService; // SubmissionService 인터페이스
import www.silver.vo.MemberVO; // MemberVO 클래스
import www.silver.vo.ProblemVO; // ProblemVO 클래스
import www.silver.vo.SubmissionVO; // SubmissionVO 클래스

import javax.inject.Inject; // 의존성 주입 어노테이션
import javax.servlet.http.HttpSession; // HTTP 세션
import java.util.Arrays; // Arrays 클래스
import java.util.HashMap; // HashMap 클래스
import java.util.List; // List 인터페이스
import java.util.Map; // Map 인터페이스

@Controller // 스프링 컨트롤러로 등록
public class ProblemController { // ProblemController 클래스 정의

    @Inject // 의존성 주입
    private ProblemService problemService; // ProblemService 객체

    @Inject // 의존성 주입
    private SubmissionService submissionService; // SubmissionService 객체

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList( // 지원 언어 목록 정의
        "Python", "JavaScript", "Java", "C++", "C", "C#", "TypeScript", "Go", "Rust", "Swift", "Kotlin", "PHP", "Ruby", "Dart", "R", "Julia"
    );

    @GetMapping("/problems") // GET 요청 처리 (/problems 경로)
    public String listProblems(HttpSession session, RedirectAttributes ra, // 문제 목록 표시 메서드
            @RequestParam(value = "language", required = false, defaultValue = "") String language, // 언어 파라미터
            @RequestParam(value = "difficulty", required = false, defaultValue = "") String difficulty, // 난이도 파라미터
            Model model) { // 모델 객체
        model.addAttribute("problemList", problemService.getProblemsByLanguageAndDifficulty(language, difficulty)); // 문제 목록 추가
        model.addAttribute("language", language); // 선택된 언어 추가
        model.addAttribute("difficulty", difficulty); // 선택된 난이도 추가
        Map<String, Object> result = new HashMap<>(); // 결과 맵 (현재 미사용)
        
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser"); // 세션에서 로그인 사용자 정보 가져오기
        if (loginUser == null) { // 로그인되지 않은 경우
           ra.addFlashAttribute("message", "로그인 상태에서 문제풀기를 할 수 있습니다."); // 메시지 추가
           return "redirect:/"; // 홈으로 리다이렉트
        }
        return "/getuseds/problemList"; // 문제 목록 페이지 반환
    }

    @GetMapping("/problem") // GET 요청 처리 (/problem 경로)
    public String showProblem(@RequestParam("id") int problemId, Model model) { // 문제 상세 표시 메서드
        System.out.println("Received problemId: " + problemId); // 디버깅 로그
        ProblemVO problem = problemService.getProblemById(problemId); // 문제 조회
        if (problem == null) { // 문제가 없는 경우
            throw new IllegalArgumentException("문제 ID " + problemId + "에 해당하는 문제가 없습니다."); // 예외 발생
        }
        String language = problem.getLanguage(); // 문제 언어 가져오기
        boolean languageSupported = SUPPORTED_LANGUAGES.contains(language); // 지원 언어 여부 확인
        model.addAttribute("problem", problem); // 문제 데이터 추가
        model.addAttribute("languageSupported", languageSupported); // 언어 지원 여부 추가
        return "/getuseds/problem"; // 문제 상세 페이지 반환
    }

    @PostMapping("/submitSolution") // POST 요청 처리 (/submitSolution 경로)
    public String submitSolution(@RequestParam("problemId") int problemId, // 코드 제출 처리 메서드
                                @RequestParam("code") String code, // 제출된 코드
                                @RequestParam("language") String language, // 선택된 언어
                                HttpSession session, // 세션 객체
                                Model model) { // 모델 객체
        ProblemVO problem = problemService.getProblemById(problemId); // 문제 조회
        String feedback = submissionService.gradeSubmission(code, problem.getDescription(), language); // 코드 채점

        MemberVO id = (MemberVO) session.getAttribute("loginUser"); // 로그인 사용자 정보 가져오기
        if (id == null) { // 로그인되지 않은 경우
            throw new IllegalStateException("로그인 정보가 없습니다."); // 예외 발생
        }

        SubmissionVO submission = new SubmissionVO(); // 제출 객체 생성
        submission.setUserId(id.getUserId()); // 사용자 ID 설정
        submission.setProblemId(problemId); // 문제 ID 설정
        submission.setCode(code); // 제출 코드 설정
        submission.setLanguage(language); // 언어 설정
        submission.setFeedback(feedback); // 피드백 설정
        submission.setCorrect(feedback.startsWith("Correct")); // 정답 여부 설정

        submissionService.saveSubmission(submission); // 제출 데이터 저장

        model.addAttribute("problem", problem); // 문제 데이터 추가
        model.addAttribute("feedback", feedback); // 피드백 추가
        model.addAttribute("isCorrect", submission.isCorrect()); // 정답 여부 추가
        model.addAttribute("submittedCode", code); // 제출된 코드 추가
        return "/getuseds/problem"; // 문제 상세 페이지 반환
    }
}