// Creates a reusable `Card` component with built-in tapping+indication
import 'package:flutter/material.dart';

class RootCard extends StatelessWidget {
  final VoidCallback onTap;
  final Widget child;

  const RootCard({
    super.key,
    required this.onTap,
    required this.child,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: InkWell(
        onTap: onTap,
        child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [Expanded(child: child)],
            )),
      ),
    );
  }
}
