import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var viewModel = ContentViewModel()


	var body: some View {
        VStack {
            ForEach(viewModel.categories, id: \.id) { category in
                Text(
                    "\(category.name)"
                )
            }
        }
        .onAppear {
            viewModel.loadCategories()
        }
	}
}

class ContentViewModel: ObservableObject {
    @Published var categories: [CategoryEntity] = []

    private let repo = RootRecordsRepository(databaseDriverFactory: DriverFactory())

    func loadCategories() {
        categories = repo.getCategories()
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
