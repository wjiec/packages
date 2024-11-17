//
//  CarouselView.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI
import Combine

struct CarouselView<Element, Content>: View where Content: View {
    /// The collection of underlying identified data that SwiftUI uses to create
    /// views dynamically.
    private let items: [Element]

    /// A function to create content on demand using the underlying data.
    private let content: (Element) -> Content
    
    @Binding private var autoScroll: Bool
    
    init(_ items: [Element], @ViewBuilder content: @escaping (Element) -> Content) {
        self.items = items
        self.content = content
        self._autoScroll = Binding.constant(false)
    }
    
    init(_ items: [Element], autoScroll: Binding<Bool>, @ViewBuilder content: @escaping (Element) -> Content) {
        self.items = items
        self.content = content
        self._autoScroll = autoScroll
    }

    @State private var currentIndex = 0

    var body: some View {
        TabView(selection: $currentIndex) {
            ForEach(items.indices, id: \.self) { index in
                content(items[index])
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
                    .tag(index)
            }
        }
        .tabViewStyle(.page(indexDisplayMode: .never))
        .onAppear { startAutoScrollIfNeeded() }
        .onChange(of: autoScroll) { startAutoScrollIfNeeded() }
    }
    
    @State private var timerCancellable: Cancellable?
    
    private func startAutoScrollIfNeeded() {
        timerCancellable?.cancel()
        if autoScroll {
            timerCancellable = Timer.publish(every: 2.5, on: .main, in: .common)
                .autoconnect()
                .sink(receiveValue: { _ in
                    withAnimation {
                        currentIndex = (currentIndex + 1) % items.count
                    }
                })
        }
    }
}

#Preview {
    let modelData = ModelData()
    
    CarouselView(modelData.landmarks, autoScroll: .constant(true)) { landmark in
        ZStack(alignment: .bottom) {
            landmark.image
                .resizable()
                .scaledToFill()
        }
    }
    .environment(modelData)
}
