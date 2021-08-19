import 'package:json_annotation/json_annotation.dart';

part 'report.g.dart';

@JsonSerializable(explicitToJson: true)
class Report {
  final String payeeName;
  final double amount;


  Report(
      {required this.amount,
        required this.payeeName});

  factory Report.fromJson(Map<String, dynamic> json) =>
      _$ReportFromJson(json);

  Map<String, dynamic> toJson() => _$ReportToJson(this);
}
