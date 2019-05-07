package com.exam.examservice.controller;


import com.exam.examservice.beans.Admin;
import com.exam.examservice.beans.Student;
import com.exam.examservice.beans.Teacher;
import com.exam.examservice.mapper.AdminMapper;
import com.exam.examservice.mapper.StudentMapper;
import com.exam.examservice.mapper.TeacherMapper;
import com.exam.examservice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/login")
public class UserLoginController {


    public String validCode="";

//    @Autowired
//    private UserLoginService userLoginService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/userLogin")
    public Map<String, Object> userLogin(@RequestParam("userName") String userName
            , @RequestParam("password") String password, @RequestParam("role") String role
            ,@RequestParam("code") String code, HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        System.out.println("-----------"+validCode);
        validCode=validCode.toUpperCase();
        code=code.toUpperCase();
        System.out.println("****"+validCode+"------"+code);
        Admin admin = adminMapper.findByUserName(userName, MD5Utils.encode(password), role);
        Student student = studentMapper.findByUserName(userName, MD5Utils.encode(password), role);
        Teacher teacher = teacherMapper.findByUserName(userName, MD5Utils.encode(password), role);
//        if ((userName == null || userName == "") || (password == null || password == "")) {
//            result.put("code", "400");
//            result.put("msg", "账号或密码不能为空");
//            return result;
        if (!code.equals(validCode)){
            result.put("code", "400");
            result.put("msg", "验证码错误！");
            return result;
        } else {
            if (admin != null) {
                result.put("code", 200);
                result.put("msg", "登录成功");
                System.out.println(userName);
                session.setAttribute("loginUser", userName);
                return result;
            }else if (student != null) {
                result.put("code", 200);
                result.put("msg", "登录成功");
                System.out.println(userName);
                session.setAttribute("loginUser", userName);
                return result;
            }else if (teacher != null) {
                result.put("code", 200);
                result.put("msg", "登录成功");
                System.out.println(userName);
                session.setAttribute("loginUser", userName);
                return result;
            } else  {
                result.put("code", 400);
                result.put("msg", "账号密码错误或角色不匹配");
                return result;
            }
        }
    }
    @RequestMapping("/gainValidCode")
    public ResponseEntity<byte[]> gainValidCode() {
        ResponseEntity<byte[]> responseEntity = null;
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentDispositionFormData("attachment", System.currentTimeMillis() + ".jpg");
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache");
        headers.setExpires(-1);

        byte[] b = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Map<String, Object> map = generateCodeAndPic();
            validCode= String.valueOf(map.get("code"));
            System.out.println("*****"+validCode);
            ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", byteArrayOutputStream);

            b = byteArrayOutputStream.toByteArray();

            responseEntity = new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<byte[]>(b, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private static int width = 90;// 定义图片的width
    private static int height = 20;// 定义图片的height
    private static int codeCount = 4;// 定义图片上显示验证码的个数
    private static int xx = 15;
    private static int fontHeight = 18;
    private static int codeY = 16;
    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 生成一个map集合 code为生成的验证码 codePic为生成的验证码BufferedImage对象
     *
     * @return
     */
    public static Map<String, Object> generateCodeAndPic() {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        // 存放验证码
        map.put("code", randomCode);
        // 存放生成的验证码BufferedImage对象
        map.put("codePic", buffImg);
        return map;
    }
}
