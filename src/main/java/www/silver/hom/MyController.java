package www.silver.hom;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	@Resource(name = "cname") // �����̳ʷκ��� ���� �޴´�
	String name; //���� null�� �ƴ� ���Թ��� ��ü�� �ּ� < DI����
	
	
}
