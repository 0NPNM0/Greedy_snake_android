package com.example.project_2;

public class Snake {
    int x = 0;
    int y = 0;
    private Snake nextBody = null; //由蛇头指向蛇尾

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Snake getNextBody() {
        return nextBody;
    }

    public void setNextBody(Snake nextBody) {
        this.nextBody = nextBody;
    }
}
