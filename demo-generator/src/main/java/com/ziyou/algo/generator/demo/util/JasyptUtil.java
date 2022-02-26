import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptUtil {

  public static void main(String[] args) {
    String account = "root";
    String password = "123456";
    BasicTextEncryptor encryptor = new BasicTextEncryptor();
    //秘钥
    //encryptor.setPassword(System.getProperty("jasypt.encryptor.password"));
    encryptor.setPassword("eug83f3gG");
    //密码进行加密
    String newAccount = encryptor.encrypt(account);
    String newPassword = encryptor.encrypt(password);
    System.out.println("加密后账号：" + newAccount);
    System.out.println("加密后密码：" + newPassword);
  }
}
