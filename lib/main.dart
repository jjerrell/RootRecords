import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:root_records/model/task.dart';
import 'package:root_records/provider/theme_notifier.dart';
import 'package:root_records/view/settings_view.dart';
import 'package:root_records/view/task_edit_view.dart';
import 'package:root_records/view/task_view.dart';
import 'db/database_helper.dart';

void main() {
  runApp(ChangeNotifierProvider(
      create: (context) => ThemeNotifier(), child: const MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return Consumer<ThemeNotifier>(builder: (context, themeNotifier, child) {
      return MaterialApp(
          title: 'RootRecords',
          theme: themeNotifier.currentTheme,
          home: const MyHomePage(title: 'Gardening Diary'));
    });
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final DatabaseHelper _databaseHelper = DatabaseHelper();
  List<Task> _tasks = [];

  @override
  void initState() {
    super.initState();
    _loadTasks();
  }

  Future<void> _loadTasks() async {
    List<Task> tasks = await _databaseHelper.getTasks();
    tasks.sort((lhsTask, rhsTask) {
      return lhsTask.date.compareTo(rhsTask.date);
    });
    setState(() {
      _tasks = tasks;
    });
  }

  Future<void> _addTask(Task task) async {
    await _databaseHelper.insertTask(task);
    _loadTasks();
  }

  Future<void> _updateTask(Task task) async {
    await _databaseHelper.updateTask(task);
    _loadTasks();
  }

  Future<void> _deleteTask(int id) async {
    await _databaseHelper.deleteTask(id);
    _loadTasks();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        actions: [
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SettingsView()),
              );
            },
          ),
        ],
      ),
      body: Expanded(
        child: _tasks.isEmpty
            ? const Center(
                child: Text(
                  'Get started by adding your first task!',
                  style: TextStyle(fontSize: 18),
                ),
              )
            : ListView.builder(
                itemCount: _tasks.length,
                itemBuilder: (context, index) {
                  Task task = _tasks[index];
                  int? taskId = task.id;
                  return TaskView(
                    key: ValueKey(taskId),
                    task: task,
                    onCardTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => TaskEditPage(
                            task: task,
                            onSave: (updatedTask) {
                              _updateTask(updatedTask);
                            },
                          ),
                        ),
                      );
                    },
                    onDelete: () {
                      if (taskId != null) {
                        _deleteTask(taskId);
                      }
                    },
                  );
                },
              ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          // Example of adding a task
          Task newTask = Task(
            name: 'New Task',
            date: DateTime.now(),
            description: 'This is a new task description',
          );
          await _addTask(newTask);
        },
        tooltip: 'Add Entry',
        child: const Icon(Icons.add),
      ),
    );
  }
}
