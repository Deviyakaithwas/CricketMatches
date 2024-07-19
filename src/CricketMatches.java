
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CricketMatches {
    public static void main(String[] args) {
        String apiKey = "cricket-matches@5";
        String apiUrl = "https://api.cuvora.com/car/partner/cricket-data";
        List<Match> matches = getMatchesFromAPI(apiUrl, apiKey);
        if (matches != null) {
            int highestScore = findHighestScore(matches);
            String highestScoreTeam = findTeamName(matches, highestScore);
            int countMatchesWith300PlusScore = countMatchesWith300PlusScore(matches);
            System.out.println("Highest Score : " + highestScore + " and Team Name is : " + highestScoreTeam);
            System.out.println("Number Of Matches with total 300 Plus Score: " + countMatchesWith300PlusScore);
        } else {
            System.out.println("Failed to fetch data from API.");
        }
    }

    private static <JsonElement> List<Match> getMatchesFromAPI(String apiUrl, String apiKey) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("apiKey", apiKey);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            Gson gson = new GsonBuilder().create();
            JsonElement jsonElement = JsonParser.parseString(content.toString());
            JsonElement matchesArray = jsonElement.getAsJsonArray();
            List<Match> matches = new ArrayList<>();
            for (JsonElement match : matchesArray.getAsJsonArray()) {
                matches.add(gson.fromJson(match, Match.class));
            }
            return matches;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int findHighestScore(List<Match> matches) {
        int highestScore = 0;
        for (Match match : matches) {
            if (match.getT1s() > highestScore) {
                highestScore = match.getT1s();
            }
            if (match.getT2s() > highestScore) {
                highestScore = match.getT2s();
            }
        }
        return highestScore;
    }

    private static String findTeamName(List<Match> matches, int highestScore) {
        for (Match match : matches) {
            if (match.getT1s() == highestScore) {
                return match.getT1();
            }
            if (match.getT2s() == highestScore) {
                return match.getT2();
            }
        }
        return null;
    }

    private static int countMatchesWith300PlusScore(List<Match> matches) {
        int count = 0;
        for (Match match : matches) {
            if (match.getT1s() + match.getT2s() >= 300) {
                count++;
            }
        }
        return count;
    }

    static class Match {
        private String id;
        private String dateTimeGMT;
        private String matchType;
        private String status;
        private String ms;
        private String t1;
        private String t2;
        private int t1s;
        private int t2s;
        private String t1img;
        private String t2img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDateTimeGMT() {
            return dateTimeGMT;
        }

        public void setDateTimeGMT(String dateTimeGMT) {
            this.dateTimeGMT = dateTimeGMT;
        }

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getT1() {
            return t1;}

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getT2() {
            return t2;
        }

        public void setT2(String t2) {
            this.t2 = t2;
        }

        public int getT1s() {
            return t1s;
        }

        public void setT1s(int t1s) {
            this.t1s = t1s;
        }

        public int getT2s() {
            return t2s;
        }

        public void setT2s(int t2s) {
            this.t2s = t2s;
        }

        public String getT1img() {
            return t1img;
        }

        public void setT1img(String t1img) {
            this.t1img = t1img;
        }

        public String getT2img() {
            return t2img;
        }

        public void setT2img(String t2img) {
            this.t2img = t2img;
        }
    }
}