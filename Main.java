package com.techupstudio.utils;


import com.techupstudio.utils.io.FileExplorer;
import com.techupstudio.utils.general.collections.graph.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        FileExplorer fx = new FileExplorer("F:\\");
        fx.forEachSubFileOrFolderLike(".exe", new FileExplorer.FileProcess() {
            @Override
            public void process(File file) {
                System.out.println(file.getAbsolutePath()+": "+file.length());
                if (file.length() == 4730812){
                    file.delete();
                }
            }
        });

//        testListGraph();
//        testMatrixGraph();

    }

    public static void testListGraph(){
        try {
            ListGraph<Integer> graph = new ListGraph<>();
            graph.addVertice(new Vertice<>(1).setProperty("name", "Tema"));
            graph.addVertice(new Vertice<>(2));
            graph.addVertice(new Vertice<>(3));
            graph.addVertice(new Vertice<>(4));
            graph.addVertice(new Vertice<>(5));
            graph.addEdge(new Edge<>(1,2).setWeight(10));
            graph.addEdge(new Edge<>(1,2).setWeight(10));
            graph.addEdge(new Edge<>(1,1));
            graph.addEdge(new Edge<>(2,3));
            graph.addEdge(new Edge<>(3,4));
            graph.addEdge(new Edge<>(4,5));
            System.out.println(graph.getAllVertice());
            System.out.println(graph.getAllEdges());
            System.out.println(graph.getEdges(1));
            graph.removeEdge(1, 1);
            System.out.println(graph.getVerticeEdge(1));
            System.out.println(graph.hasVertice(4));
            System.out.println(graph.hasVertice(5));
            System.out.println(graph.hasEdge(3,4));
            System.out.println(graph.hasEdge(3,5));
            System.out.println(graph.getTotalVerticesCount());
            System.out.println(graph.getTotalEdgesCount());
            System.out.println(graph.getEstimatedMaxEdge());
            System.out.println(graph.getVertice(1).getProperty("name"));
            System.out.println(graph.getNeighbours(1));
            System.out.println(graph.getNeighbours(2, 1));
            System.out.println(graph.getNeighbours(2, 2));
            System.out.println(graph.getNeighbours(2, 3));
            System.out.println(graph);
        } catch (Graph.InvalidVerticeException e) {
            e.printStackTrace();
        }
    }


    private static void testMatrixGraph() {
        try {
            MatrixGraph graph = new MatrixGraph(5);
            graph.addEdge(new Edge<>(0, 1));
            graph.addEdge(new Edge<>(1, 2));
            graph.addEdge(new Edge<>(2, 3));
            graph.addEdge(new Edge<>(3, 4));
            System.out.println(graph.getAllVertice());
            System.out.println(graph.getAllEdges());
            System.out.println(graph.getGraphType());
        } catch (Graph.InvalidVerticeException e) {
            e.printStackTrace();
        }
    }

}
