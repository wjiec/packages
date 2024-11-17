//
//  BadgeBackground.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct BadgeBackground: View {
    var body: some View {
        GeometryReader { geometry in
            Path() { path in
                // Using the smallest of the geometry’s two dimensions preserves the
                // aspect ratio of the badge when its containing view isn’t square.
                var width: CGFloat = min(geometry.size.width, geometry.size.height)
                let height = width
                
                // Scale the shape on the x-axis using xScale, and then add xOffset
                // to recenter the shape within its geometry.
                let xScale: CGFloat = 0.832
                let xOffset = (width * (1.0 - xScale)) / 2.0
                width *= xScale
                
                // The move(to:) method moves the drawing cursor within the bounds of a shape
                // as though an imaginary pen or pencil is hovering over the area, waiting to
                // start drawing.
                path.move(
                    to: CGPoint(
                        x: width * 0.95 + xOffset,
                        y: height * (0.20 + HexagonParameters.adjustment)
                    )
                )
                
                // Draw the lines for each point of the shape data to create a rough hexagonal shape.
                HexagonParameters.segments.forEach { segment in
                    // The addLine(to:) method takes a single point and draws it. Successive calls
                    // to addLine(to:) begin a line at the previous point and continue to the new point.
                    path.addLine(
                        to: CGPoint(
                            x: width * segment.line.x + xOffset,
                            y: height * segment.line.y
                        )
                    )
                    
                    // Use the addQuadCurve(to:control:) method to draw the Bézier curves
                    // for the badge’s corners.
                    path.addQuadCurve(
                        to: CGPoint(
                            x: width * segment.curve.x + xOffset,
                            y: height * segment.curve.y
                        ),
                        control: CGPoint(
                            x: width * segment.control.x + xOffset,
                            y: height * segment.control.y
                        )
                    )
                }
            }
            .normalized() // Why here?
            .fill(.linearGradient(
                Gradient(colors: [Self.gradientStart, Self.gradientEnd]),
                startPoint: UnitPoint(x: 0.5, y: 0),
                endPoint: UnitPoint(x: 0.5, y: 0.6)
            ))
        }
        // By preserving a 1:1 aspect ratio, the badge maintains its position
        // at the center of the view, even if its ancestor views aren’t square.
        .aspectRatio(1, contentMode: .fit)
    }
    
    static let gradientStart = Color(red: 239.0 / 255, green: 120.0 / 255, blue: 221.0 / 255)
    static let gradientEnd = Color(red: 239.0 / 255, green: 172.0 / 255, blue: 120.0 / 255)
}

#Preview {
    BadgeBackground()
}
