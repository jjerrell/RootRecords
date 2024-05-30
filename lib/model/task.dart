import 'dart:convert';

class Task {
  final int? id;
  final String name;
  final DateTime date;
  final String description;

  Task({
    this.id,
    required this.name,
    required this.date,
    required this.description,
  });

  // Factory method for creating a new Task instance from a map (e.g., from JSON)
  factory Task.fromMap(Map<String, dynamic> map) {
    return Task(
      id: map['id'],
      name: map['name'],
      date: DateTime.parse(map['date']),
      description: map['description'],
    );
  }

  // Method for converting a Task instance to a map (e.g., to JSON)
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'date': date.toIso8601String(),
      'description': description,
    };
  }

  // Method for creating a Task instance from a JSON string
  factory Task.fromJson(String source) => Task.fromMap(json.decode(source));

  // Method for converting a Task instance to a JSON string
  String toJson() => json.encode(toMap());
}
