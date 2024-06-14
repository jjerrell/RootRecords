import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:root_records/view/settings_category_view.dart';
import '../provider/theme_notifier.dart';

class SettingsView extends StatefulWidget {
  const SettingsView({super.key});

  @override
  State<SettingsView> createState() => _SettingsViewState();
}

class _SettingsViewState extends State<SettingsView> {
  // bool _notificationEnabled = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
      ),
      body: ListView(
        children: [
          const ListTile(
            title: Text(
              "General",
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          ListTile(
            leading: const Icon(Icons.category),
            title: const Text('Edit Categories'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => const SettingsCategoryView()),
              );
            },
          ),
          // SwitchListTile(
          //   title: const Text('Enable Notifications'),
          //   value: _notificationEnabled,
          //   onChanged: (bool value) {
          //     setState(() {
          //       _notificationEnabled = value;
          //     });
          //   },
          //   secondary: const Icon(Icons.notifications),
          // ),
          Consumer<ThemeNotifier>(
            builder: (context, themeNotifier, child) {
              return SwitchListTile(
                title: const Text('Enable Dark Theme'),
                value: themeNotifier.isDarkTheme,
                onChanged: (bool value) {
                  themeNotifier.toggleTheme();
                },
                secondary: const Icon(Icons.dark_mode),
              );
            },
          ),
          const Divider(),
          const ListTile(
            title: Text(
              "About",
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          const ListTile(
            leading: Icon(Icons.info),
            title: Text('App Version'),
            subtitle: Text('1.0.0'),
          ),
        ],
      ),
    );
  }
}
