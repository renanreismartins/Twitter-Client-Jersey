package br.com.tobrocado.twitterclient;


import static br.com.tobrocado.twitterclient.Settings.*;
import br.com.tobrocado.twitterclient.Status;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;


@Path("/feed")
public class FeedResource {

    @GET
    @Produces("text/html")
    public Response getFeed() {

        if (!isAuthorized()) {
            return requestToken();
        }

        String token = getToken();

        Client client = Client.create();

        OAuthSecrets oAuthSecrets = new OAuthSecrets()
                .consumerSecret(CONSUMER_SECRET)
                .tokenSecret(getTokenSecret());

        OAuthParameters oAuthParameters = new OAuthParameters()
                .consumerKey(CONSUMER_KEY).token(token)
                .signatureMethod("HMAC-SHA1").version("1.0");

        // coloca os tokens de autenticação no header
        client.addFilter(new OAuthClientFilter(client.getProviders(), oAuthParameters, oAuthSecrets));

        // realiza o get nesta url
        ClientResponse response = client.resource(FRIENDS_TIMELINE_URL)
                .get(ClientResponse.class);
        
        // obtém os objetos conforme o formato de resposta da URL acima
        // http://dev.twitter.com/console?content_type=xml&path=statuses/friends_timeline
        List<Status> statuses = response.getEntity(new GenericType<List<Status>>(){});

        // Manda para o template /feed.jsp
        return Response.ok(new Viewable("/feed", statuses)).build();
    }

    private Response requestToken() {
        Client client = Client.create();

        OAuthSecrets oAuthSecrets = new OAuthSecrets()
                .consumerSecret(CONSUMER_SECRET);

        OAuthParameters oAuthParameters = new OAuthParameters()
                .consumerKey(CONSUMER_KEY)
                .signatureMethod("HMAC-SHA1").version("1.0");

       client.addFilter(new OAuthClientFilter(client.getProviders(), oAuthParameters, oAuthSecrets));

       Form response = client.resource(REQUEST_TOKEN_URL).post(Form.class);

       Settings.setToken(response.getFirst(oAuthParameters.TOKEN),
               response.getFirst(oAuthParameters.TOKEN_SECRET),
               false);

       return Response.seeOther(
               UriBuilder.fromPath(AUTHORIZE_URL)
                .queryParam(OAuthParameters.TOKEN, response.getFirst(OAuthParameters.TOKEN))
                .build()
               ).build();
    }
}
