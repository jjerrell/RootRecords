import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../db/database_helper.dart';
import '../model/category.dart';
import '../model/task.dart';
import 'component/root_card.dart';

class TaskView extends StatelessWidget {
  final Task task;
  final VoidCallback onDelete;
  final VoidCallback onCardTap;

  const TaskView({
    super.key,
    required this.task,
    required this.onDelete,
    required this.onCardTap,
  });

  Future<Category?> _getCategory(int? categoryId) async {
    if (categoryId != null) {
      return await DatabaseHelper().getCategoryById(categoryId);
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    String formattedDate = DateFormat.yMMMd().format(task.date);

    return FutureBuilder<Category?>(
      future: _getCategory(task.categoryId),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return CircularProgressIndicator();
        } else if (snapshot.hasError) {
          return Text('Error loading category');
        } else {
          Category? category = snapshot.data;
          return Dismissible(
            key: Key(
                "${task.id ?? task.name}"), // Use a unique key for Dismissible
            direction: DismissDirection.endToStart,
            background: Container(
              color: Colors.red,
              alignment: Alignment.centerRight,
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: const Icon(
                Icons.delete,
                color: Colors.white,
              ),
            ),
            onDismissed: (direction) {
              if (direction == DismissDirection.endToStart) {
                onDelete();
              }
            },
            child: RootCard(
              onTap: onCardTap,
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  if (category != null)
                    Padding(
                      padding: const EdgeInsets.only(right: 8.0),
                      child: CircleAvatar(
                        radius: 8, // Small indicator
                        backgroundColor: Color(category.color),
                      ),
                    ),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          task.name,
                          style: const TextStyle(
                              fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 8),
                        Text(
                          'Date: $formattedDate',
                          style: const TextStyle(fontSize: 16),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          );
        }
      },
    );
  }
}
