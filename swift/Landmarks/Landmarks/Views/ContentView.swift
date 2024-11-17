//
//  ContentView.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct ContentView: View {
    @State var selection: Tab = .Featured
    enum Tab {
        case Featured
        case List
    }
    
    var body: some View {
        // The tag(_:) modifier on each of the views matches one of the possible
        // values that the selection property can take so the TabView can coordinate
        // which view to display when the user makes a selection in the user interface.
        TabView(selection: $selection) {
            CategoryHome()
                .tabItem({
                    Label("Featured", systemImage: "star")
                })
                .tag(Tab.Featured)
            
            LandmarkList()
                .tabItem({
                    Label("List", systemImage: "list.bullet")
                })
                .tag(Tab.List)
        }
        .transition(.slide)
    }
}

#Preview {
    ContentView()
        // add the model object to the environment, which makes the
        // object available to any subview.
        .environment(ModelData())
}
