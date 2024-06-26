import SwiftUI
import shared

struct ContentView: View {
    let repo = RootRecordsRepository(databaseDriverFactory: DriverFactory())
	let greet = Greeting().greet()

	var body: some View {
        VStack {
            ForEach(repo.getCategories(), id: \.id) { category in
                Text(
                    "\(category.name)"
                )
            }
        }
		Text(greet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
