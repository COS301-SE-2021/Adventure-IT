import 'package:flutter/foundation.dart' show kIsWeb;

final adventureApi = kIsWeb ? "localhost:9001" : "10.0.2.2:9001";
final budgetApi = kIsWeb ? "localhost:9007" : "10.0.2.2:9007";
final itineraryApi = kIsWeb ? "localhost:9009" : "10.0.2.2:9009";
final checklistApi = kIsWeb ? "localhost:9008" : "10.0.2.2:9008";
final userApi = kIsWeb ? "http://localhost:9002" : "http://10.0.2.2:9002";
final authApiGetToken = kIsWeb
    ? "http://localhost:8080/auth/realms/adventure-it/protocol/openid-connect/token"
    : "http://10.0.2.2:8080/auth/realms/adventure-it/protocol/openid-connect/token";
final authApiAdmin = kIsWeb
    ? "http://localhost:8080/auth/admin/realms/adventure-it/"
    : "http://10.0.2.2:8080/auth/admin/realms/adventure-it/";
final mainApi = kIsWeb ? "localhost:9999" : "10.0.2.2:9999";
final googleMapsKey = "AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";
final chatApi=kIsWeb?"localhost:9010":"10.0.2.2:9010";
final mediaApi=kIsWeb?"localhost:9005":"10.0.2.2:9005";
