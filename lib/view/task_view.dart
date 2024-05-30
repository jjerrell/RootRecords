import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../model/task.dart';

class TaskView extends StatelessWidget {
  final Task task;
  final VoidCallback onCardTap;
  final VoidCallback onDelete;
  @override
  final Key key;

  const TaskView({
    required this.key,
    required this.task,
    required this.onCardTap,
    required this.onDelete,
  });

  @override
  Widget build(BuildContext context) {
    // Format the date as a simple date string
    String formattedDate = DateFormat.yMMMd().format(task.date);

    return Dismissible(
      key: key,
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
      child: InkWell(
        onTap: onCardTap, // Action when the card is tapped
        child: Card(
          margin: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(
                      child: Text(
                        task.name,
                        style: const TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 8), // Use height instead of width
                Text(
                  'Date: $formattedDate',
                  style: const TextStyle(fontSize: 16),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
