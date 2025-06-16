#!/bin/bash

## Seed management DATA to identityhubs
API_KEY="c3VwZXItdXNlcg==.c3VwZXItc2VjcmV0LWtleQo="

# add consumer participant
echo
echo
echo "Create consumer participant"
CONSUMER_CONTROLPLANE_SERVICE_URL="http://[INSERT CONSUMER IP]:8082"
CONSUMER_IDENTITYHUB_URL="http://[INSERT CONSUMER IP]:7082"
DATA_CONSUMER=$(jq -n --arg url "$CONSUMER_CONTROLPLANE_SERVICE_URL" --arg ihurl "$CONSUMER_IDENTITYHUB_URL" '{
           "roles":[],
           "serviceEndpoints":[
             {
                "type": "CredentialService",
                "serviceEndpoint": "\($ihurl)/api/presentation/v1/participants/[INSERT CONSUMER DID AS BASE64 STRING]",
                "id": "consumer-credentialservice-1"
             },
             {
                "type": "ProtocolEndpoint",
                "serviceEndpoint": "\($url)/api/dsp",
                "id": "consumer-dsp"
             }
           ],
           "active": true,
           "participantId": "did:web:[INSERT CONSUMER IP]%3A7083:consumer",
           "did": "did:web:[INSERT CONSUMER IP]%3A7083:consumer",
           "key":{
               "keyId": "did:web:[INSERT CONSUMER IP]%3A7083:consumer#key-1",
               "privateKeyAlias": "did:web:[INSERT CONSUMER IP]%3A7083:consumer#key-1",
               "keyGeneratorParams":{
                  "algorithm": "EC"
               }
           }
       }')

curl --location 'http://localhost:7081/api/identity/v1alpha/participants/' \
--header 'Content-Type: application/json' \
--header "x-api-key: $API_KEY" \
--data "$DATA_CONSUMER"
