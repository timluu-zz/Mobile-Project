/*
 * Copyright 2012 Topmedia Company Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.topmedia.client;

/**
 * Game play command
 *
 * @author Tran Anh Tuan <tuanta@topmedia.vn>
 */
public final class GameCommand {

    /**
     * Game Over. Stop and close network connection to server.
     */
    public static final int GAME_PLAY_GAME_OVER = 0;
    /**
     * Create player.
     */
    public static final int GAME_PLAY_CREATE_PLAYER = 1;
    /**
     * Move player to x and y.
     */
    public static final int GAME_PLAY_MOVE_PLAYER = 2;
    /**
     * Create bullet.
     */
    public static final int GAME_PLAY_CREATE_BULLET = 3;
}