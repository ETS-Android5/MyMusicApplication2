package com.peter.android.mymusicapplication.apollo;

import com.apollographql.apollo.ApolloClient;

public class ApolloFactory {
    private static final String BASE_URL = "https://api-eu-central-1.graphcms.com/v2/ckh970hwk098301z79sgxarya/master";

    private static ApolloClient apolloClient;

    public static ApolloClient getApolloClient() {

        if (apolloClient == null) {

            apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .build();

        }

        return apolloClient;
    }
}
