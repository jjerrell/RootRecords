import 'package:flutter_test/flutter_test.dart';
import 'package:path/path.dart';
import 'package:root_records/db/database_helper.dart';
import 'package:root_records/model/task.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  setUpAll(() async {
    sqfliteFfiInit();
    databaseFactory = databaseFactoryFfi;
  });

  group('DatabaseHelper Tests', () {
    late DatabaseHelper databaseHelper;
    late String dbPath;

    setUp(() async {
      dbPath = join(await getDatabasesPath(), 'app_database.db');
      databaseHelper = DatabaseHelper();
      await databaseHelper.database;
    });

    tearDown(() async {
      await databaseHelper.close();
      await databaseFactory.deleteDatabase(dbPath);
    });

    test('Insert and retrieve a task', () async {
      final task = Task(
        name: 'Test Task',
        date: DateTime.now(),
        description: 'Test Description',
        categoryId: null,
      );

      final _ = await databaseHelper.insertTask(task);
      final retrievedTasks = await databaseHelper.getTasks();

      expect(retrievedTasks.length, 1);
      expect(retrievedTasks.first.name, 'Test Task');
      expect(retrievedTasks.first.description, 'Test Description');
    });

    test('Update a task', () async {
      final task = Task(
        name: 'Test Task',
        date: DateTime.now(),
        description: 'Test Description',
        categoryId: null,
      );

      final id = await databaseHelper.insertTask(task);
      final updatedTask = Task(
        id: id,
        name: 'Updated Task',
        date: DateTime.now(),
        description: 'Updated Description',
        categoryId: null,
      );

      await databaseHelper.updateTask(updatedTask);
      final retrievedTasks = await databaseHelper.getTasks();

      expect(retrievedTasks.length, 1);
      expect(retrievedTasks.first.name, 'Updated Task');
      expect(retrievedTasks.first.description, 'Updated Description');
    });

    test('Delete a task', () async {
      final task = Task(
        name: 'Test Task',
        date: DateTime.now(),
        description: 'Test Description',
        categoryId: null,
      );

      final id = await databaseHelper.insertTask(task);
      await databaseHelper.deleteTask(id);
      final retrievedTasks = await databaseHelper.getTasks();

      expect(retrievedTasks.length, 0);
    });
  });
}
