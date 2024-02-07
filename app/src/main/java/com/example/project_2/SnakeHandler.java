package com.example.project_2;

import java.util.Random;

public class SnakeHandler {

    private ItemType[][] currentGameData = new ItemType[15][15];
    private Direction currentDirection = Direction.DOWN;

    private int foodX = 0;
    private int foodY = 0;

    private Snake snakeHead = new Snake(0, 0);
    private int snakeLength = 1;

    public SnakeHandlerDelegate delegate = null;


    public SnakeHandler() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                currentGameData[i][j] = ItemType.EMPTY;
            }
        }
        currentGameData[0][0] = ItemType.SNAKE; //蛇的初始位置设置为左上角
        snakeHead.x = 0;
        snakeHead.y = 0;

        generateFoodRandomly();
    }

    private void generateFoodRandomly() {
        Random random = new Random();
        int row = random.nextInt(15);
        int col = random.nextInt(15);
        this.foodX = col;
        this.foodY = row;
        if(currentGameData[row][col] == ItemType.EMPTY) {
            currentGameData[row][col] = ItemType.FOOD;
        }else if(currentGameData[row][col] != ItemType.EMPTY){
            generateFoodRandomly();
        }
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void go() {
        ItemType frontItem = checkFrontItem(currentDirection);
        switch(frontItem) {
            case SNAKE:
                //todo: 反向前进则继续走
                onGameOver(); //撞到自己，游戏结束
                return;
            case EMPTY:
                moveSnake(currentDirection);
                break;
            case FOOD:
                onEatFood();
                generateFoodRandomly();
                break;
        }
    }



    private void onEatFood() {
        Snake newHead = new Snake(foodX, foodY); //吃到食物，生长一节身体
        newHead.setNextBody(snakeHead);
        snakeHead = newHead;
        currentGameData[foodY][foodX] = ItemType.SNAKE;
        snakeLength += 1;

//        generateFoodRandomly();

        delegate.onScoreChanged(snakeLength);
    }

    private void moveSnake(Direction direction) {
        //向前走一步
        Snake tailBody = getSnakeBodyWithOffset(snakeLength - 1);
        Snake secondLastBody = getSnakeBodyWithOffset(snakeLength - 2);
        secondLastBody.setNextBody(null); //和尾部断开
        currentGameData[tailBody.y][tailBody.x] = ItemType.EMPTY;
        tailBody.setNextBody(snakeHead); //尾部变头部
        switch(direction) {
            case UP:
                if (snakeHead.y == 0) {
                    tailBody.x = snakeHead.x;
                    tailBody.y = 14; //翻到下面
                } else {
                    tailBody.x = snakeHead.x;
                    tailBody.y = snakeHead.y - 1;
                }
                break;
            case RIGHT:
                if (snakeHead.x == 14) {
                    tailBody.y = snakeHead.y;
                    tailBody.x = 0;
                } else {
                    tailBody.y = snakeHead.y;
                    tailBody.x = snakeHead.x + 1;
                }
                break;
            case DOWN:
                if (snakeHead.y == 14) {
                    tailBody.x = snakeHead.x;
                    tailBody.y = 0;
                } else {
                    tailBody.x = snakeHead.x;
                    tailBody.y = snakeHead.y + 1;
                }
                break;
            case LEFT:
                if (snakeHead.x == 0) {
                    tailBody.y = snakeHead.y;
                    tailBody.x = 14;
                } else {
                    tailBody.y = snakeHead.y;
                    tailBody.x = snakeHead.x - 1;
                }
                break;
        }
        this.snakeHead = tailBody;
        currentGameData[snakeHead.y][snakeHead.x] = ItemType.SNAKE;
    }

    private ItemType checkFrontItem(Direction direction) {
        //检查前方物体
        ItemType result = ItemType.EMPTY;
        switch(direction) {
            case UP:
                if(snakeHead.y == 0) {
                    result = currentGameData[14][snakeHead.x]; //翻到下面
                } else {
                    result = currentGameData[snakeHead.y - 1][snakeHead.x];
                }
                break;
            case RIGHT:
                if(snakeHead.x == 14) {
                    result = currentGameData[snakeHead.y][0];
                } else {
                    result = currentGameData[snakeHead.y][snakeHead.x + 1];
                }
                break;
            case DOWN:
                if(snakeHead.y == 14) {
                    result = currentGameData[0][snakeHead.x];
                } else {
                    result = currentGameData[snakeHead.y + 1][snakeHead.x];
                }
                break;
            case LEFT:
                if(snakeHead.x == 0) {
                    result = currentGameData[snakeHead.y][14];
                } else {
                    result = currentGameData[snakeHead.y][snakeHead.x - 1];
                }
                break;
        }
        return result;
    }

    private void onGameOver() {
        if(this.delegate != null) {
            delegate.onGameOver();
        }
    }

    private Snake getSnakeBodyWithOffset(int offset) {
        if(offset < 0) {
            offset = 0;
        }
        Snake body = snakeHead;
        for(int i=0; i<offset; i++) {
            body = body.getNextBody();
        }
        return body;
    }

    public ItemType[][] getGameData() {
        return currentGameData;
    }

    public void clean() {
        snakeHead = new Snake(0, 0);
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                currentGameData[i][j] = ItemType.EMPTY;
            }
        }
        snakeLength = 1;
        currentDirection = Direction.DOWN;
        currentGameData[0][0] = ItemType.SNAKE; //蛇的初始位置设置为左上角
        generateFoodRandomly();

        delegate.onScoreChanged(1);
    }


}
