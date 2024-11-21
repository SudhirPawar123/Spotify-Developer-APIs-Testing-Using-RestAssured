package spotify.app;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class TestingSpotify {
String Token ="BQATZ3hLB_cgU18SdVR6aDUkYlhaz4KjgLAbuKYk4ZYpRr-GS97-4LkEwUX8eAR8iCazkoS20W5bQQZASq77HdiHoe0C7W3twxagvjgyuiWm9epVhJo9NCBdMw6QN6WeFH8_VNFhgmKwMxLFa8UvB28ttwFT9jdxCneBn9o3j3qVrRpaej_A4wgT0YxD9-Q0Se7K_AqF834fdU8dbiP0fXT9aqe74ea8CeJ5z61khBXqu3wMMgtWsfUww4Mp6tlZQd3VfvUQsAK9ziXJ6Fyz_f6Mo6gagDMhGkFUFN5tI2_CbGFYiRQsmyclD6ZXxEVJGs9yjz-r_HPzDPeeUHX0Hgc";
String Id = null;
String playListId=null;

//-------------USER - Spotify Testing ----------------------------------------------

        @Test
        public void getCurrentProfile() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/me");

            response.prettyPrint();
            Id = response.path("id");
            Assert.assertEquals(response.getStatusCode(), 200);

        }

    @Test(dependsOnMethods = "getCurrentProfile")
    public void createPlaylist() {
        //   System.out.println(Id+"  this is id");
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \"Arijit New Playlist\",\n" +
                        "    \"description\": \"New playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/"+Id+"/playlists");
        playListId=response.path("id");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
    }

        @Test(dependsOnMethods = "getCurrentProfile")
        public void getUserProfile() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/users/" + Id);

            response.prettyPrint();


            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getUserTopArtists() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("time_range", "medium_term")
                    .queryParam("limit", 10)
                    .queryParam("offset", 5)
                    .when()
                    .get("https://api.spotify.com/v1/me/top/artists");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

           @Test(dependsOnMethods = "createPlaylist")
           public void followPlaylist() {

            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("type", "playlist")

                    .when()
                    .put("https://api.spotify.com/v1/playlists/"+playListId+"/followers");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

    @Test(dependsOnMethods = "createPlaylist")
    public void unfollowPlaylist() {

        System.out.println(playListId+"<-- This is PlaylistId");
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .delete("https://api.spotify.com/v1/playlists/" + playListId+ "/followers");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getFollowedArtists() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/me/following?type=artist");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void followArtistsOrUsers() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("type", "artist")
                    .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                    .when()
                    .put("https://api.spotify.com/v1/me/following");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 204);
        }

        @Test
        public void unfollowArtistsOrUsers() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("type", "artist")
                    .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                    .when()
                    .delete("https://api.spotify.com/v1/me/following");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 204);
        }

        @Test
        public void checkIfUserFollowsArtistsOrUsers() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("type", "artist")
                    .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                    .when()
                    .get("https://api.spotify.com/v1/me/following/contains");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

    @Test(dependsOnMethods = "createPlaylist")
    public void checkIfCurrentUserFollowsPlaylist() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("ids", "jmperezperez")
                    .when()
                    .get("https://api.spotify.com/v1/playlists/"+playListId+"/followers/contains");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }

        //----------------Tracks - Spotify Testing -------------------------------------
        @Test
        public void CheckUserSavedAudiobooks() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/tracks/contains?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getTrack() {
            Response trackResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl?market=ES");

            Assert.assertEquals(trackResponse.getStatusCode(), 200);
        }

        @Test
        public void getSeveralTracks() {
            Response tracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/tracks?market=ES&ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

            Assert.assertEquals(tracksResponse.getStatusCode(), 200);
        }

        @Test
        public void getUserSavedTracks() {
            Response savedTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/tracks?market=ES&limit=10&offset=5");

            Assert.assertEquals(savedTracksResponse.getStatusCode(), 200);
        }

        @Test
        public void saveTracksForCurrentUser() {
            Response saveTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\"ids\": [\"4uLU6hMCjMI75M1A2tKUQC\"]}")
                    .when()
                    .put("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

            Assert.assertEquals(saveTracksResponse.getStatusCode(), 200);
        }

        @Test
        public void removeUserSavedTracks() {
            Response removeTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\"ids\": [\"4VqPOruhp5EdPBeR92t6lQ\"]}")
                    .when()
                    .delete("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

            Assert.assertEquals(removeTracksResponse.getStatusCode(), 200);
        }

        @Test
        public void checkUserSavedTracks() {
            Response checkTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/tracks/contains?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

            Assert.assertEquals(checkTracksResponse.getStatusCode(), 200);
        }

        @Test
        public void getSeveralTracksAudioFeatures() {
            Response audioFeaturesResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audio-features?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

            Assert.assertEquals(audioFeaturesResponse.getStatusCode(), 200);
        }

        @Test
        public void getTrackAudioFeatures() {
            Response audioFeaturesResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audio-features/11dFghVXANMlKmJXsNCbNl");

            Assert.assertEquals(audioFeaturesResponse.getStatusCode(), 200);
        }

        @Test
        public void getTrackAudioAnalysis() {
            Response audioAnalysisResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audio-analysis/11dFghVXANMlKmJXsNCbNl");

            Assert.assertEquals(audioAnalysisResponse.getStatusCode(), 200);
        }

        @Test
        public void getRecommendations() {
            Response recommendationsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/recommendations?limit=10&market=ES&seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical%2Ccountry&seed_tracks=0c6xIDDpzE81m2q797ordA");

            Assert.assertEquals(recommendationsResponse.getStatusCode(), 200);
        }

        //    --------------- Shows  - Spotify Testing ----------------------------
        @Test(dependsOnMethods = "getCurrentProfile")
        public void getShow() {
            System.out.println(Id);
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", "Bearer " + Token)
                    .get("https://api.spotify.com/v1/shows/"+Id+"?market=ES");
            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 400);

        }

        @Test
        public void getSeveralShows() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/shows?market=ES&ids=5CfCWKI5pZ28U0uOzXkDHe,5as3aKmN2k11yfDDDSrvaZ");
            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);


        }

        @Test
        public void getShowEpisodes() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/shows/38bS44xjbVVZ3No3ByF1dJ/episodes");
            response.prettyPrint();
        }

        @Test
        public void getUserSavedShows() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", Token)
                    .when()
                    .get("https://api.spotify.com/v1/me/shows");
            response.prettyPrint();
        }

        @Test
        public void saveShowsforCurrentUser() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", Token)
                    .when()
                    .get("https://api.spotify.com/v1/me/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
            response.prettyPrint();
        }

        @Test
        public void removeUserSavedShows() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .delete("https://api.spotify.com/v1/me/shows?ids=5CfCWKI5pZ28U0uOzXkDHe,5as3aKmN2k11yfDDDSrvaZ&market=ES");
            response.then().assertThat().statusCode(200);
        }

        @Test
        public void CheckUserSavedShows() {
            Response response = given()
                    .accept("application/json")
                    .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                    .when()
                    .get("https://api.spotify.com/v1/me/shows/contains?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
            response.prettyPrint();
        }
//---------------------SEARCH  - Spotify Testing ------------------------------

        @Test
        public void searchForItem() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)

                    .when()
                    .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=artist");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
//-----------------PLAYLIST  - Spotify Testing ----------------------------

    @Test(dependsOnMethods = "createPlaylist")
    public void getPlaylist() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .when()
                    .get("https://api.spotify.com/v1/playlists/"+playListId);

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

    @Test(dependsOnMethods = "createPlaylist")
        public void changePlaylistDetails() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .body("\n" +
                            "    \"name\": \"Updated Playlist Name\",\n" +
                            "    \"description\": \"Updated playlist description\",\n" +
                            "    \"public\": false\n" +
                            "}")
                    .when()
                    .put("https://api.spotify.com/v1/playlists/"+playListId);

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

    @Test(dependsOnMethods = "createPlaylist")
        public void getPlaylistItems() {
            Response response = given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Token)
                    .queryParam("market", "ES")
                    .queryParam("fields", "items(added_by.id,track(name,href,album(name,href)))")
                    .queryParam("limit", 10)
                    .queryParam("offset", 5)
                    .when()
                    .get("https://api.spotify.com/v1/playlists/"+playListId+"/tracks");

            response.prettyPrint();


            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void updatePlaylistItems() {

            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "    \"range_start\": 0,\n" +
                            "    \"insert_before\": 10,\n" +
                            "    \"range_length\": 2\n" +
                            "}")
                    .when()
                    .put("https://api.spotify.com/v1/playlists/7voYYZDBEtdO6whLlQIhdg/tracks?uris=");
            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


    @Test(dependsOnMethods = "createPlaylist")
        public void addItemsToPlaylist() {

            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{{\"uris\": [\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\",\"spotify:track:1301WleyT98MSxVHPZCA6M\", \"spotify:episode:512ojhOuo1ktJprKbVcKyQ\"]}")
                    .when()
                    .post("https://api.spotify.com/v1/playlists/"+playListId+"/tracks?position=0&uris=spotify:track:4iV5W9uYEdYUVa79Axb7Rh,spotify:track:1301WleyT98MSxVHPZCA6M,spotify:episode:512ojhOuo1ktJprKbVcKyQ");


            response.prettyPrint();


            Assert.assertEquals(response.getStatusCode(), 201);
        }


    @Test(dependsOnMethods = "createPlaylist")
        public void removeItemsFromPlaylist() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{ \"tracks\": [{ \"uri\": \"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\" },{ \"uri\": \"spotify:track:1301WleyT98MSxVHPZCA6M\" }] }")
                    .when()
                    .delete("https://api.spotify.com/v1/playlists/"+playListId+"/tracks ");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        @Test
        public void getCurrentUserPlaylists() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/playlists?limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }



        @Test
        public void getUserPlaylists() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/users/"+Id+"/playlists?limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }





        @Test
        public void getFeaturedPlaylists() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/browse/featured-playlists?locale=sv_SE&limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }



        @Test
        public void getCategoryPlaylists() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/browse/categories/dinner/playlists?limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getPlaylistCoverImage() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/images");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }




        //    -----------------------PLAYER  - Spotify Testing ----------------------------
        @Test
        public void getPlaybackState() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/player?market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 204);
        }

        @Test
        public void transferPlayback() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"device_ids\": [\n" +
                            "    \"74ASZWbe4lXaubB36ztrGX\"\n" +
                            "  ]\n" +
                            "}")
                    .when()
                    .put("https://api.spotify.com/v1/me/player");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }



        @Test
        public void getAvailableDevices() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/player/devices");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getCurrentlyPlayingTrack() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/player/currently-playing?market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 204);
        }

        @Test
        public void startResumePlayback() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                            "  \"offset\": {\n" +
                            "    \"position\": 5\n" +
                            "  },\n" +
                            "  \"position_ms\": 0\n" +
                            "}")
                    .when()
                    .post("https://api.spotify.com/v1/me/player/play");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 405);
        }

        @Test
        public void pausePlayback() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/player/pause");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void skipToNext() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("https://api.spotify.com/v1/me/player/next?device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }

        @Test
        public void skipToPrevious() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("https://api.spotify.com/v1/me/player/previous?device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void seekToPosition() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/player/seek?position_ms=25000&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void setRepeatMode() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/player/repeat?state=context&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void setPlaybackVolume() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/player/volume?volume_percent=50&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void togglePlaybackShuffle() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/player/shuffle?state=true&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }



        @Test
        public void getRecentlyPlayedTracks() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/player/recently-played?limit=10&after=1484811043508");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        @Test
        public void getUserQueue() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/player/queue");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }


        @Test
        public void addItemToPlaybackQueue() {
            String uri = "spotify:track:4iV5W9uYEdYUVa79Axb7Rh";
            String deviceId = "0d1841b0976bae2a3a310dd74c0f3df354899bc8";

            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("https://api.spotify.com/v1/me/player/queue?uri=" + uri + "&device_id=" + deviceId);

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 403);
        }

//------------------Markets  - Spotify Testing --------------------------------


        @Test
        public void getAvailableMarkets() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/markets");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
//-----------------Genres  - Spotify Testing -------------------


        @Test
        public void getAvailableGenreSeeds() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
        //-----------------------Episodes-  - Spotify Testing -------------------------------------
        @Test
        public void getEpisode() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/episodes/"+Id+"?market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 400);
        }


        @Test
        public void getSeveralEpisodes() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/episodes?ids=77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf&market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
        @Test
        public void getUsersSavedEpisodes() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/episodes?market=ES&limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
        @Test
        public void saveEpisodesForCurrentUser() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"ids\": [\n" +
                            "    \"77o6BIVlYM3msb4MMIL1jH\",\n" +
                            "    \"0Q86acNRm6V9GYx55SXKwf\"\n" +
                            "  ]\n" +
                            "}")
                    .when()
                    .put("https://api.spotify.com/v1/me/episodes");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void removeUserSavedEpisodes() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"ids\": [\n" +
                            "    \"7ouMYWpwJ422jRcDASZB7P\",\n" +
                            "    \"4VqPOruhp5EdPBeR92t6lQ\",\n" +
                            "    \"2takcwOaAZWiXQijPHIx7B\"\n" +
                            "  ]\n" +
                            "}")
                    .when()
                    .delete("https://api.spotify.com/v1/me/episodes");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void checkUserSavedEpisodes() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/episodes/contains?ids=77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        //    ------------------- Chapters  - Spotify Testing -----------------------------------
        @Test
        public void getChapter() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/chapters/"+Id+"?market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 400);
        }



        @Test
        public void getSeveralChapters() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/chapters?ids=0IsXVP0JmcB2adSE338GkK,3ZXb8FKZGU0EHALYX6uCzU,0D5wENdkdwbqlrHoaJ9g29&market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        //------------------------ Categories  - Spotify Testing -----------------
        @Test
        public void getSeveralBrowseCategories() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/browse/categories?locale=sv-ES&limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        @Test
        public void getSingleBrowseCategory() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/browse/categories/dinner?locale=sv-EU");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }



        //    -------------------Audiobook  - Spotify Testing -----------------
        @Test
        public void getAudiobook() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audiobooks/"+Id+"?market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 400);
        }


        @Test
        public void getSeveralAudiobooks() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe&market=ES");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void getAudiobookChapters() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/audiobooks/"+Id+"/chapters?market=ES&limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 400);
        }

        @Test
        public void getUsersSavedAudiobooks() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/audiobooks?limit=10&offset=5");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }
        @Test
        public void saveAudiobooksForUser() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test
        public void removeSavedAudiobooksForUser() {
            String requestBody = "{\n" +
                    "  \"ids\": [\n" +
                    "    \"18yVqkdbdRvS24c0Ilj2ci\",\n" +
                    "    \"1HGw3J3NxZO1TP1BTtVhpZ\",\n" +
                    "    \"7iHfbu1YPACw6oZPAFJtqe\"\n" +
                    "  ]\n" +
                    "}";

            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .delete("https://api.spotify.com/v1/me/audiobooks");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        @Test
        public void checkUserSavedAudiobooks() {
            Response response = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/audiobooks/contains?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");

            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200);
        }


        //    -----------------Artists------------------------------------------
        @Test
        public void getArtist() {
            Response artistResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg");

            Assert.assertEquals(artistResponse.getStatusCode(), 200);
        }


        @Test
        public void getSeveralArtists() {
            Response artistsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6");

            Assert.assertEquals(artistsResponse.getStatusCode(), 200);
        }
        @Test
        public void getArtistsAlbums() {
            Response albumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums?include_groups=single%2Cappears_on&market=ES&limit=10&offset=5");

            Assert.assertEquals(albumsResponse.getStatusCode(), 200);
        }


        @Test
        public void getArtistsTopTracks() {
            Response topTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?market=ES");

            Assert.assertEquals(topTracksResponse.getStatusCode(), 200);
        }

        @Test
        public void getArtistsRelated() {
            Response relatedArtistsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/related-artists");

            Assert.assertEquals(relatedArtistsResponse.getStatusCode(), 200);
        }

//-----------------------Albums---------------------------------------

        @Test
        public void getAlbum() {
            Response albumResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy?market=ES");

            Assert.assertEquals(albumResponse.getStatusCode(), 200);
        }
        @Test
        public void getSeveralAlbums() {
            Response albumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc&market=ES");

            Assert.assertEquals(albumsResponse.getStatusCode(), 200);
        }


        @Test
        public void getAlbumTracks() {
            Response albumTracksResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks?market=ES&limit=10&offset=5");

            Assert.assertEquals(albumTracksResponse.getStatusCode(), 200);
        }



        @Test
        public void getUsersSavedAlbums() {
            Response savedAlbumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/albums?limit=10&offset=5&market=ES");

            Assert.assertEquals(savedAlbumsResponse.getStatusCode(), 200);
        }


        @Test
        public void saveAlbumsForCurrentUser() {

            Response saveAlbumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");

            Assert.assertEquals(saveAlbumsResponse.getStatusCode(), 200);
        }
        @Test
        public void removeUsersSavedAlbums() {
            String requestBody = "{\"ids\": [\"382ObEPsp2rxGrnsizN5TX\", \"1A2GTWGtFfWp7KSQTwWOyo\", \"2noRn2Aes5aoNVsU6iWThc\"]}";

            Response removeAlbumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .delete("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");

            Assert.assertEquals(removeAlbumsResponse.getStatusCode(), 200);
        }
        @Test
        public void checkUsersSavedAlbums() {
            Response checkAlbumsResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/me/albums/contains?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");

            Assert.assertEquals(checkAlbumsResponse.getStatusCode(), 200);
        }


        @Test
        public void getNewReleases() {
            Response newReleasesResponse = given()
                    .header("Authorization", "Bearer " + Token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("https://api.spotify.com/v1/browse/new-releases?limit=10&offset=5");

            Assert.assertEquals(newReleasesResponse.getStatusCode(),200);
        }



}
