import 'package:flutter/material.dart';

class ThemeNotifier extends ChangeNotifier {
  bool _isDarkTheme = false;

  bool get isDarkTheme => _isDarkTheme;

  ThemeData get currentTheme => _isDarkTheme ? darkTheme : lightTheme;

  void toggleTheme() {
    _isDarkTheme = !_isDarkTheme;
    notifyListeners();
  }
}

final ThemeData lightTheme = ThemeData(
  primarySwatch: Colors.green,
  brightness: Brightness.light,
  appBarTheme: const AppBarTheme(
    color: Colors.green,
  ),
  colorScheme: const ColorScheme.light(
    primary: Colors.green,
    secondary: Colors.greenAccent,
  ),
);

final ThemeData darkTheme = ThemeData(
  primarySwatch: Colors.green,
  brightness: Brightness.dark,
  appBarTheme: const AppBarTheme(
    color: Colors.green,
  ),
  colorScheme: const ColorScheme.dark(
    primary: Colors.green,
    secondary: Colors.greenAccent,
  ),
);
