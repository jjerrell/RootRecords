import 'package:flutter/material.dart';

import '../db/database_helper.dart';
import '../model/category.dart';

class CategoryNotifier extends ChangeNotifier {
  List<Category> _categories = [];
  final DatabaseHelper _databaseHelper = DatabaseHelper();

  List<Category> get categories => _categories;

  CategoryNotifier() {
    _loadCategories();
  }

  Future<void> _loadCategories() async {
    _categories = await _databaseHelper.getCategories();
    notifyListeners();
  }

  Future<void> addCategory(Category category) async {
    await _databaseHelper.insertCategory(category);
    await _loadCategories();
  }

  Future<void> updateCategory(Category category) async {
    await _databaseHelper.updateCategory(category);
    await _loadCategories();
  }

  Future<void> deleteCategory(int id) async {
    await _databaseHelper.deleteCategory(id);
    await _loadCategories();
  }
}
