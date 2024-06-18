import 'package:flutter_test/flutter_test.dart';
import 'package:root_records/model/task.dart';

void main() {
  group('Task Model Tests', () {
    test('Task toMap and fromMap', () {
      final task = Task(
        id: 1,
        name: 'Test Task',
        date: DateTime.parse('2023-01-01'),
        description: 'Test Description',
        categoryId: 1,
      );

      final taskMap = task.toMap();
      final newTask = Task.fromMap(taskMap);

      expect(newTask.id, task.id);
      expect(newTask.name, task.name);
      expect(newTask.date, task.date);
      expect(newTask.description, task.description);
      expect(newTask.categoryId, task.categoryId);
    });
  });
}
