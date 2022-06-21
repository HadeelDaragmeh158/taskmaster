export type AmplifyDependentResourcesAttributes = {
    "api": {
        "amplifyDatasource": {
            "GraphQLAPIKeyOutput": "string",
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    },
    "auth": {
        "taskmaster": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string"
        }
    },
    "analytics": {
        "taskmaster": {
            "Region": "string",
            "Id": "string",
            "appName": "string"
        }
    },
    "predictions": {
        "translateText2117f290": {
            "region": "string",
            "sourceLang": "string",
            "targetLang": "string"
        },
        "speechGenerator652e261d": {
            "region": "string",
            "language": "string",
            "voice": "string"
        }
    }
}