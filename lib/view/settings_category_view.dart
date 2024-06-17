import 'package:flutter/material.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
import 'package:provider/provider.dart';
import 'package:root_records/view/widget/category_list.dart';

import '../model/category.dart';
import '../provider/category_notifier.dart';

class SettingsCategoryView extends StatelessWidget {
  const SettingsCategoryView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Manage Categories'),
      ),
      body: CategoryList(
        trailingWidgetBuilder: (category) {
          return Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              IconButton(
                icon: const Icon(Icons.edit),
                onPressed: () {
                  _showCategoryDialog(context, category);
                },
              ),
              IconButton(
                icon: const Icon(Icons.delete),
                onPressed: () {
                  Provider.of<CategoryNotifier>(context, listen: false)
                      .deleteCategory(category.id!);
                },
              ),
            ],
          );
        },
      ),
    );
  }

  void _showCategoryDialog(BuildContext context, [Category? category]) {
    final nameController = TextEditingController(text: category?.name);
    Color selectedColor =
        category != null ? Color(category.color) : Colors.blue;

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text(category == null ? 'Add Category' : 'Edit Category'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: nameController,
                decoration: const InputDecoration(labelText: 'Name'),
              ),
              const SizedBox(height: 16),
              const Text('Color:'),
              const SizedBox(height: 16),
              GestureDetector(
                onTap: () {
                  _showColorPicker(context, selectedColor, (color) {
                    selectedColor = color;
                  });
                },
                child: Container(
                  height: 50,
                  width: double.infinity,
                  color: selectedColor,
                  alignment: Alignment.center,
                  child: const Text(
                    'Pick Color',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                final name = nameController.text;
                final color = selectedColor.value;
                if (category == null) {
                  Provider.of<CategoryNotifier>(context, listen: false)
                      .addCategory(Category(name: name, color: color));
                } else {
                  final updatedCategory =
                      Category(id: category.id, name: name, color: color);
                  Provider.of<CategoryNotifier>(context, listen: false)
                      .updateCategory(updatedCategory);
                }
                Navigator.of(context).pop();
              },
              child: const Text('Save'),
            ),
          ],
        );
      },
    );
  }

  void _showColorPicker(BuildContext context, Color currentColor,
      ValueChanged<Color> onColorChanged) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Pick a color'),
          content: SingleChildScrollView(
            child: ColorPicker(
              pickerColor: currentColor,
              onColorChanged: onColorChanged,
              pickerAreaHeightPercent: 0.8,
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Got it'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
