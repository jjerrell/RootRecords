import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../model/category.dart';
import '../../provider/category_notifier.dart';

class CategoryList extends StatelessWidget {
  final ValueChanged<Category>? onCategorySelected;
  final Widget Function(Category)? trailingWidgetBuilder;

  const CategoryList({
    super.key,
    this.onCategorySelected,
    this.trailingWidgetBuilder,
  });

  @override
  Widget build(BuildContext context) {
    return Consumer<CategoryNotifier>(
      key: key,
      builder: (context, categoryNotifier, child) {
        return ListView(
          children: [
            ...categoryNotifier.categories.map((category) {
              return ListTile(
                title: Text(category.name),
                onTap: () {
                  if (onCategorySelected != null) {
                    onCategorySelected!(category);
                  }
                },
                leading: Container(
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    border: Border.all(
                      color: Theme.of(context).brightness == Brightness.dark
                          ? Colors.white
                          : Colors.black,
                      width: 0.5,
                    ),
                  ),
                  child: CircleAvatar(
                    backgroundColor: Color(category.color),
                  ),
                ),
                trailing: trailingWidgetBuilder != null
                    ? trailingWidgetBuilder!(category)
                    : null,
              );
            }),
          ],
        );
      },
    );
  }
}
