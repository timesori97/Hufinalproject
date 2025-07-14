package www.silver.hom; // ��Ű�� ����

import org.springframework.stereotype.Controller; // ��Ʈ�ѷ� ������̼�
import org.springframework.ui.Model; // �� ��ü
import org.springframework.web.bind.annotation.*; // �� ��û ������̼�
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // �����̷�Ʈ �Ӽ�

import www.silver.service.ProblemService; // ProblemService �������̽�
import www.silver.service.SubmissionService; // SubmissionService �������̽�
import www.silver.vo.MemberVO; // MemberVO Ŭ����
import www.silver.vo.ProblemVO; // ProblemVO Ŭ����
import www.silver.vo.SubmissionVO; // SubmissionVO Ŭ����

import javax.inject.Inject; // ������ ���� ������̼�
import javax.servlet.http.HttpSession; // HTTP ����
import java.util.Arrays; // Arrays Ŭ����
import java.util.HashMap; // HashMap Ŭ����
import java.util.List; // List �������̽�
import java.util.Map; // Map �������̽�

@Controller // ������ ��Ʈ�ѷ��� ���
public class ProblemController { // ProblemController Ŭ���� ����

    @Inject // ������ ����
    private ProblemService problemService; // ProblemService ��ü

    @Inject // ������ ����
    private SubmissionService submissionService; // SubmissionService ��ü

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList( // ���� ��� ��� ����
        "Python", "JavaScript", "Java", "C++", "C", "C#", "TypeScript", "Go", "Rust", "Swift", "Kotlin", "PHP", "Ruby", "Dart", "R", "Julia"
    );

    @GetMapping("/problems") // GET ��û ó�� (/problems ���)
    public String listProblems(HttpSession session, RedirectAttributes ra, // ���� ��� ǥ�� �޼���
            @RequestParam(value = "language", required = false, defaultValue = "") String language, // ��� �Ķ����
            @RequestParam(value = "difficulty", required = false, defaultValue = "") String difficulty, // ���̵� �Ķ����
            Model model) { // �� ��ü
        model.addAttribute("problemList", problemService.getProblemsByLanguageAndDifficulty(language, difficulty)); // ���� ��� �߰�
        model.addAttribute("language", language); // ���õ� ��� �߰�
        model.addAttribute("difficulty", difficulty); // ���õ� ���̵� �߰�
        Map<String, Object> result = new HashMap<>(); // ��� �� (���� �̻��)
        
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser"); // ���ǿ��� �α��� ����� ���� ��������
        if (loginUser == null) { // �α��ε��� ���� ���
           ra.addFlashAttribute("message", "�α��� ���¿��� ����Ǯ�⸦ �� �� �ֽ��ϴ�."); // �޽��� �߰�
           return "redirect:/"; // Ȩ���� �����̷�Ʈ
        }
        return "/getuseds/problemList"; // ���� ��� ������ ��ȯ
    }

    @GetMapping("/problem") // GET ��û ó�� (/problem ���)
    public String showProblem(@RequestParam("id") int problemId, Model model) { // ���� �� ǥ�� �޼���
        System.out.println("Received problemId: " + problemId); // ����� �α�
        ProblemVO problem = problemService.getProblemById(problemId); // ���� ��ȸ
        if (problem == null) { // ������ ���� ���
            throw new IllegalArgumentException("���� ID " + problemId + "�� �ش��ϴ� ������ �����ϴ�."); // ���� �߻�
        }
        String language = problem.getLanguage(); // ���� ��� ��������
        boolean languageSupported = SUPPORTED_LANGUAGES.contains(language); // ���� ��� ���� Ȯ��
        model.addAttribute("problem", problem); // ���� ������ �߰�
        model.addAttribute("languageSupported", languageSupported); // ��� ���� ���� �߰�
        return "/getuseds/problem"; // ���� �� ������ ��ȯ
    }

    @PostMapping("/submitSolution") // POST ��û ó�� (/submitSolution ���)
    public String submitSolution(@RequestParam("problemId") int problemId, // �ڵ� ���� ó�� �޼���
                                @RequestParam("code") String code, // ����� �ڵ�
                                @RequestParam("language") String language, // ���õ� ���
                                HttpSession session, // ���� ��ü
                                Model model) { // �� ��ü
        ProblemVO problem = problemService.getProblemById(problemId); // ���� ��ȸ
        String feedback = submissionService.gradeSubmission(code, problem.getDescription(), language); // �ڵ� ä��

        MemberVO id = (MemberVO) session.getAttribute("loginUser"); // �α��� ����� ���� ��������
        if (id == null) { // �α��ε��� ���� ���
            throw new IllegalStateException("�α��� ������ �����ϴ�."); // ���� �߻�
        }

        SubmissionVO submission = new SubmissionVO(); // ���� ��ü ����
        submission.setUserId(id.getUserId()); // ����� ID ����
        submission.setProblemId(problemId); // ���� ID ����
        submission.setCode(code); // ���� �ڵ� ����
        submission.setLanguage(language); // ��� ����
        submission.setFeedback(feedback); // �ǵ�� ����
        submission.setCorrect(feedback.startsWith("Correct")); // ���� ���� ����

        submissionService.saveSubmission(submission); // ���� ������ ����

        model.addAttribute("problem", problem); // ���� ������ �߰�
        model.addAttribute("feedback", feedback); // �ǵ�� �߰�
        model.addAttribute("isCorrect", submission.isCorrect()); // ���� ���� �߰�
        model.addAttribute("submittedCode", code); // ����� �ڵ� �߰�
        return "/getuseds/problem"; // ���� �� ������ ��ȯ
    }
}