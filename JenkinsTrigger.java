import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class JenkinsTrigger {
    private static final String JENKINS_URL = "http://your-jenkins-server/job/your-job-name/build";
    private static final String USERNAME = "your-username";
    private static final String API_TOKEN = "your-api-token";

    public static void main(String[] args) {
        try {
            URL url = new URL(JENKINS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Authentication
            String auth = USERNAME + ":" + API_TOKEN;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(new byte[0]);  // Sending empty request body
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Jenkins job triggered successfully!");
            } else {
                System.out.println("Failed to trigger Jenkins job. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
