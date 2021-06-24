import 'package:flutter/foundation.dart' show kIsWeb;

final adventureApi = kIsWeb? "localhost:9001" : "10.0.2.2:9001";
final budgetApi = kIsWeb? "localhost:9007" : "10.0.2.2:9007";