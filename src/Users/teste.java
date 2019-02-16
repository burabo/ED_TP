/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import LinkedBinaryTree.ArrayOrderedList;

/**
 *
 * @author gls
 * @param <T>
 */
public class teste<T extends Comparable> extends UsersManagement<T> {

    public void CompareCoiso(User[] users, String skill) {
        ArrayOrderedList<T> menorCaminho = new ArrayOrderedList<>();
        double menor = 9999;
        int menorUser = 0;
        int pos = 0;
        for (int i = 0; i < numVertices; i++) {
            //   for (int k = 0; k < numVertices; k++) {
            for (int j = 0; j < users[i].getSkills().length; j++) {
                if (users[i].getSkills()[j].equals(skill) && ((double) 1 / (double) users[i].getVisualizacoes()) != 0) {
                    if (((double) 1 / (double) users[i].getVisualizacoes()) < menor) {
                        menor = ((double) 1 / (double) users[i].getVisualizacoes());
                        menorUser = i;
                    }
                }

            }
            
            //         }
            if (menor != 9999) {
                menorCaminho.add(vertices[menorUser]);
            }
            menor = 9999;

        }
        System.out.println(menorCaminho.toString());

    }
}
