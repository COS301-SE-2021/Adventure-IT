import 'package:flutter/foundation.dart' show kIsWeb;

final adventureApi = kIsWeb ? "localhost:9001" : "10.0.2.2:9001";
final budgetApi = kIsWeb ? "localhost:9007" : "10.0.2.2:9007";
final itineraryApi = kIsWeb ? "localhost:9009" : "10.0.2.2:9009";
final checklistApi = kIsWeb ? "localhost:9008" : "10.0.2.2:9008";
final authApiGetToken = kIsWeb
    ? "localhost:8080/auth/realms/adventure-it/protocol/openid-connect/token"
    : "10.0.2.2:8080/auth/realms/adventure-it/protocol/openid-connect/token";
