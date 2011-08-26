package br.com.tobrocado.twitterclient;

import static br.com.tobrocado.twitterclient.Settings.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.HMAC_SHA1;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/authorized")
public class AuthorizedResource {

    @GET
    public Response get(@QueryParam(OAuthParameters.TOKEN) String token,
                        @QueryParam(OAuthParameters.VERIFIER) String verifier) {

        Client client = Client.create();
        OAuthSecrets oAuthSecrets = new OAuthSecrets()
                .consumerSecret(CONSUMER_SECRET)
                .tokenSecret(getTokenSecret());

        OAuthParameters oAuthParams = new OAuthParameters()
                .consumerKey(CONSUMER_KEY).token(getToken())
                .signatureMethod(HMAC_SHA1.NAME).version("1.0")
                .verifier(verifier);

        client.addFilter(new OAuthClientFilter(
                client.getProviders(), oAuthParams, oAuthSecrets));

        Form response = client.resource(ACCESS_TOKEN_URL).post(Form.class);

        setToken(response.getFirst(OAuthParameters.TOKEN),
                response.getFirst(OAuthParameters.TOKEN_SECRET),
                true);

        Response r = Response.seeOther(UriBuilder.fromResource(FeedResource.class).build()).build();
        
        return r;
    }
}
