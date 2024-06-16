import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:root_records/model/category.dart';
import 'package:root_records/view/widget/category_list.dart';

import '../db/database_helper.dart';
import '../model/task.dart';

class TaskEditPage extends StatefulWidget {
  final Task task;
  final ValueChanged<Task> onSave;

  const TaskEditPage({super.key, required this.task, required this.onSave});

  @override
  State<TaskEditPage> createState() => _TaskEditPageState();
}

class _TaskEditPageState extends State<TaskEditPage> {
  late TextEditingController _nameController;
  late TextEditingController _descriptionController;
  DateTime _selectedDate = DateTime.now();
  Category? _assignedCategory;

  @override
  void initState() {
    super.initState();
    _nameController = TextEditingController(text: widget.task.name);
    _descriptionController =
        TextEditingController(text: widget.task.description);
    _selectedDate = widget.task.date;
    _loadCategory(widget.task.categoryId);
  }

  Future<void> _loadCategory(int? categoryId) async {
    if (categoryId != null) {
      Category? category = await DatabaseHelper().getCategoryById(categoryId);
      setState(() {
        _assignedCategory = category;
      });
    }
  }

  @override
  void dispose() {
    _nameController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _selectedDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != _selectedDate) {
      setState(() {
        _selectedDate = picked;
      });
    }
  }

  void _onCategorySelected(Category category) {
    setState(() {
      _assignedCategory = category;
    });
  }

  void _saveTask() {
    final updatedTask = Task(
      id: widget.task.id,
      name: _nameController.text,
      date: _selectedDate,
      description: _descriptionController.text,
      categoryId: _assignedCategory?.id,
    );
    widget.onSave(updatedTask);
    Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    String formattedDate = DateFormat.yMMMd().format(_selectedDate);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Edit Task'),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            onPressed: _saveTask,
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: _nameController,
              decoration: const InputDecoration(labelText: 'Task Name'),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _descriptionController,
              decoration: const InputDecoration(labelText: 'Description'),
              maxLines: 4,
            ),
            const SizedBox(height: 16),
            Row(
              children: [
                const Text('Date:'),
                const SizedBox(width: 8),
                ElevatedButton(
                  onPressed: () => _selectDate(context),
                  child: Text(formattedDate),
                ),
                const Spacer(),
                if (_assignedCategory != null)
                  Container(
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      border: Border.all(
                        color: Theme.of(context).brightness == Brightness.dark
                            ? Colors.white
                            : Colors.black,
                        width: 2.0,
                      ),
                    ),
                    child: CircleAvatar(
                      backgroundColor: Color(_assignedCategory!.color),
                    ),
                  ),
              ],
            ),
            const SizedBox(height: 16),
            Expanded(
              child: CategoryList(
                onCategorySelected: _onCategorySelected,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
