import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../model/task.dart';
import 'component/root_card.dart';

class TaskView extends StatelessWidget {
  final Task task;
  final VoidCallback onDelete;
  final VoidCallback onCardTap;

  const TaskView({
    Key? key,
    required this.task,
    required this.onDelete,
    required this.onCardTap,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    String formattedDate = DateFormat.yMMMd().format(task.date);

    return Dismissible(
      key: Key(task.name), // Use a unique key for Dismissible
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
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              task.name,
              style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              'Date: $formattedDate',
              style: const TextStyle(fontSize: 16),
            ),
          ],
        ),
      ),
    );
  }
}
