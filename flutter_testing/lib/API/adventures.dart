import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:json_annotation/json_annotation.dart';

class Adventure {
  final String id;
  final String name;
  final String description;

  Adventure({
    required this.id,
    this.name = "",
    this.description = ""
  });

  factory Adventure.fromJson(Map<String, dynamic> json) {
    return Adventure(
      id: json['id'],
      name: json['name'],
      description: json['description'],
    );
  }
}