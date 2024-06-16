class Task {
  final int? id;
  final String name;
  final DateTime date;
  final String description;
  final int? categoryId;

  Task({
    this.id,
    required this.name,
    required this.date,
    required this.description,
    this.categoryId,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'date': date.toIso8601String(),
      'description': description,
      'category_id': categoryId,
    };
  }

  factory Task.fromMap(Map<String, dynamic> map) {
    return Task(
      id: map['id'],
      name: map['name'],
      date: DateTime.parse(map['date']),
      description: map['description'],
      categoryId: map['category_id'],
    );
  }
}
