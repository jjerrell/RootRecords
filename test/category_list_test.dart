import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:path/path.dart';
import 'package:provider/provider.dart';
import 'package:root_records/model/category.dart';
import 'package:root_records/provider/category_notifier.dart';
import 'package:root_records/view/widget/category_list.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  setUpAll(() async {
    sqfliteFfiInit();
    databaseFactory = databaseFactoryFfi;
  });

  group('CategoryList Tests', () {
    late String dbPath;
    late CategoryNotifier categoryNotifier;

    setUp(() async {
      dbPath = join(await getDatabasesPath(), 'test_app_database.db');
      categoryNotifier = CategoryNotifier();
      await Future.delayed(
          const Duration(milliseconds: 100)); // Ensure setup is complete
    });

    tearDown(() async {
      await databaseFactory.deleteDatabase(dbPath);
    });

    testWidgets('CategoryList displays categories',
        (WidgetTester tester) async {
      final categories = [
        Category(id: 1, name: 'Category 1', color: 0xFF000000),
        Category(id: 2, name: 'Category 2', color: 0xFFFFFFFF),
      ];

      categoryNotifier.setCategories(categories);

      await tester.pumpWidget(
        ChangeNotifierProvider.value(
          value: categoryNotifier,
          child: MaterialApp(
            home: Scaffold(
              body: CategoryList(
                onCategorySelected: (Category category) {},
                trailingWidgetBuilder: (Category category) {
                  return IconButton(
                    icon: const Icon(Icons.edit),
                    onPressed: () {},
                  );
                },
              ),
            ),
          ),
        ),
      );

      expect(find.text('Category 1'), findsOneWidget);
      expect(find.text('Category 2'), findsOneWidget);
      expect(find.byIcon(Icons.edit), findsNWidgets(2));
    });
  });
}
