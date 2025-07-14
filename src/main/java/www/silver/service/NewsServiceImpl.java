package www.silver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import www.silver.dao.IF_newsDAO;
import www.silver.vo.NewsVO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ���� ���� ���� ����ü. ���̹� ���� �˻� Open API�� ȣ���� IT-������ ����-����-�����Ѵ�.
 *
 * Spring Batch������ Reader �� Processor �� Writer ������ �� �޼���(fetchAndSaveNews)����
 * ���������� �����Ѵ�.
 */
@Service
public class NewsServiceImpl implements IF_newsService {

   /* =========================== DI ���� =========================== */

   /** News DAO ���� */
   @Inject
   private IF_newsDAO newsDAO;

   /** ���̹� API Client-Id (applicationContext.xml���� ����) */
   @Value("#{naverApiConfig['naver.client.id']}")
   private String clientId;

   /** ���̹� API Client-Secret */
   @Value("#{naverApiConfig['naver.client.secret']}")
   private String clientSecret;

   @PostConstruct
   public void init() {
      System.out.println("Client ID        : " + clientId + " (length=" + clientId.length() + ")");
      System.out.println("Client Secret    : " + clientSecret + " (length=" + clientSecret.length() + ")");
   }

   /**
    * Ű���� �迭�� ���鼭 API�� ȣ���ϰ�, 2025�⿡ ����� ������ DB�� �����Ѵ�. Reader �� API ȣ�� Processor ��
    * JSON �Ľ� & ������ ���� Writer �� DB insert
    */
   @Override
   public void fetchAndSaveNews() {
      System.out.println("fetchAndSaveNews() ȣ��!");

      /* -------- Step Reader ���� -------- */
      // ���� �˻� Ű���� ���
      String[] keywords = {
            // AI / ������ 
            "�ΰ�����", "������ AI", "��Ը� ��� ��", "�ӽŷ���", "������", "Agentic AI", "AI �Ź��ͽ�", "AI ���ӱ�", "GPU", "NPU", "TPU",
            "������ �м�", "MLOps", "���� AI", "������ Ʈ��",

            // Ŭ���� / ������ 
            "Ŭ���� ��ǻ��", "��ƼŬ����", "���� ��ǻ��", "��������", "�����Ƽ��", "��Ŀ", "DevOps", "CI/CD", "����ũ�μ���", "������ ����", "SDN",
            "SD-WAN", "NFV",

            // ���� 
            "��������", "���̹� ����", "Ŭ���� ����", "��ŷ", "��������", "����Ʈ����Ʈ", "���̹� ����", "������ ����", "AIOps",
            "Post-Quantum Cryptography", "Zero-Knowledge Proof", "������ �����̹���",

            /* ���ü�� / Web3 */
            "���ü��", "Web3", "NFT", "��ȣȭ��", "�����̺�����",

            /* �ϵ���� / ��Ʈ��ŷ */
            "���� ��ǻ��", "Post-Quantum Security", "FPGA", "RISC-V", "Ĩ��", "������ǻ��", "HPC", "5G", "6G", "���� ���ͳ�",
            "Starlink", "LoRa", "�������� 7",

            /* XR / ��Ÿ���� */
            "��Ÿ����", "��������", "��������", "ȥ������", "Spatial Computing",

            /* �κ� / �ڵ�ȭ */
            "�κ�����", "����� �κ�", "�����κ�", "AMR", "RPA", "�κ� ���μ��� �ڵ�ȭ", "��������",

            /* ���� ���°� */
            "�繰 ���ͳ�", "IoT ����", "���α׷��� ���", "���¼ҽ�", "�鿣��", "����Ʈ����", "�ο��ڵ�", "���ڵ�", "�������",
            "�� ����", "���� ����", "�� ����",

            // �������å 
            "����ũ", "����� ����", "�������", "������ ��ȯ", "ESG ���", "�׸� IT",

            // ���� Ưȭ
            "K-��ũ", "�Ｚ SDS", "LG CNS", "SK C&C", "��������", "Ŭ���� ����", "K-����Ʈ��Ƽ",
            
            // ������ �̺�Ʈ
            "������ ���۷���", "������ ���̳�", "������ �Ծ�", "��Ŀ��", 
            "�ڵ� ��Ʈķ��", "������ ��ũ��", "��ũ��",

            // ���� ��� ������ �̺�Ʈ
            "NAVER DEVIEW", "�佺 SLASH", "�Ｚ ������ ���۷���", 
            "īī�� ������ ���۷���", "���� ������ ����", "���� ������ ���۷���",
            "�������ũ���̳�", "NHN ������", "LG CNS ������ ����"

         

            
      };

      /* -------- Step Reader ���� �� -------- */

      for (String keyword : keywords) {

         /* -------- Step Reader ó�� -------- */
         // ���̹� API ȣ�� �� JSON ����
         String json = callNaverNewsApi(keyword);
         System.out.println("���̹� API ����(JSON) : " + json);
         /* -------- Step Reader ó�� �� -------- */

         /* -------- Step Processor ó�� -------- */
         // JSON �� List<NewsVO> ��ȯ
         List<NewsVO> newsList = parseNewsJson(json, keyword);
         /* -------- Step Processor ó�� �� -------- */

         for (NewsVO news : newsList) {

            /* --- Step Processor �ļ� ���͸� --- */
            // 2025�� ������ ����
            if (news.getPubDate() != null && news.getPubDate().toString().startsWith("2025")) {

               /* -------- Step Writer ó�� -------- */
               // DB INSERT
               newsDAO.insertNews(news);
               /* -------- Step Writer ó�� �� -------- */
            }
         }
      }
      System.out.println("fetchAndSaveNews() ����!");
   }

   /* ========================== ��ȸ ���� ========================== */

   /** 2025�� IT-������ ������ ������ ��ȸ */
   @Override
   public List<NewsVO> get2025ItNews(int page, int pageSize) {
      int start = (page - 1) * pageSize;
      return newsDAO.select2025ItNews(start, pageSize);
   }

   /** ��ü ���� �Ǽ� ��ȸ */
   @Override
   public int getTotalNewsCount() {
      return newsDAO.selectTotalNewsCount();
   }

   /* ======================= ���� ���� �޼��� ======================= */

   /* ------------------------- Reader ���� ------------------------- */
   /**
    * ���̹� ���� �˻� Open API ȣ�� �� ���� JSON ���ڿ��� ��ȯ�Ѵ�.
    */
   private String callNaverNewsApi(String keyword) {
      try {
         System.out.println("[API ȣ��] Ű����: " + keyword);

         /* 1) ���� �Ķ���� ���ڵ� */
         String text = URLEncoder.encode(keyword, "UTF-8");

         /* 2) ��û URL ���� */
         String apiURL = "https://openapi.naver.com/v1/search/news.json" + "?query=" + text + "&display=100"
               + "&sort=date";
         System.out.println("��û URL: " + apiURL);

         /* 3) HTTP ���� */
         URL url = new URL(apiURL);
         HttpURLConnection con = (HttpURLConnection) url.openConnection();
         con.setRequestMethod("GET");

         /* 4) ���� ��� �߰� */
         con.setRequestProperty("X-Naver-Client-Id", clientId);
         con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

         /* 5) ���� �ڵ� Ȯ�� �� ��Ʈ�� ���� */
         int responseCode = con.getResponseCode();
         System.out.println("���� �ڵ�: " + responseCode);

         InputStream is = (responseCode == 200) ? con.getInputStream() : con.getErrorStream();

         /* 6) ���� Body �б� */
         BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         StringBuilder sb = new StringBuilder();
         String line;
         while ((line = br.readLine()) != null)
            sb.append(line);
         br.close();

         return sb.toString();
      } catch (Exception e) {
         System.err.println("API ȣ�� �� ���� �߻�");
         e.printStackTrace();
         return null;
      }
   }

   /* ------------------------ Processor ���� ----------------------- */
   /**
    * ���̹� ���� JSON�� �Ľ��Ͽ� ������ ��ü(NewsVO) ����Ʈ�� ��ȯ�Ѵ�.
    */
   private List<NewsVO> parseNewsJson(String json, String keyword) {
      List<NewsVO> newsList = new ArrayList<>();

      try {
         ObjectMapper mapper = new ObjectMapper();
         JsonNode root = mapper.readTree(json);
         JsonNode items = root.path("items");

         // ���̹� API ��¥ ���� ��) "Fri, 20 Jun 2025 09:30:00 +0900"
         SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

         for (JsonNode item : items) {
            NewsVO news = new NewsVO();

            news.setTitle(item.path("title").asText());
            news.setOriginallink(item.path("originallink").asText());
            news.setLink(item.path("link").asText());
            news.setDescription(item.path("description").asText());

            // �Խ� ��¥ �Ľ�
            String pubDateStr = item.path("pubDate").asText();
            Date date = sdf.parse(pubDateStr);
            news.setPubDate(new Timestamp(date.getTime()));

            // �˻� Ű���� �� ī�װ�, ���� �÷��� ����
            news.setQuery(keyword);
            news.setCategory(keyword);

            newsList.add(news);
         }
      } catch (Exception e) {
         System.err.println("JSON �Ľ� �� ���� �߻�");
         e.printStackTrace();
      }
      return newsList;
   }
}
