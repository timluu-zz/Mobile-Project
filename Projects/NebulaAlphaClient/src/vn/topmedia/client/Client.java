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

import examples.nebulaalpha.NebulaAlpha;
import vn.topmedia.commons.StringUtils;
import vn.topmedia.message.Message;
import vn.topmedia.message.games.DisconnectPlayerMessage;
import vn.topmedia.network.SocketClient;

/**
 *
 * @author Tran Anh Tuan <tuanta@topmedia.vn>
 */
public class Client extends SocketClient {

    NebulaAlpha nebulaAlpha;
    String inPlayer;

    public Client(NebulaAlpha inNebulaAlpha, String address, int port) {
        super(address, port);
        nebulaAlpha = inNebulaAlpha;

    }

    public void processMessage(Message message) {
        switch (message.command) {
            case GameCommand.GAME_PLAY_GAME_OVER:
                inPlayer = nebulaAlpha.inPlayer.getName();
                String content = new String(message.data);
                System.out.println("Player now " + inPlayer);
                System.out.println("Player request disconnect " + content);
                if (inPlayer.equals(content)) {
                    close();
                } else {
                    nebulaAlpha.removePlayer(content);
                }
                break;
            case GameCommand.GAME_PLAY_CREATE_PLAYER:
                content = new String(message.data);
                String[] data = StringUtils.split(content, ";");
                if (data != null && data.length == 3) {
                    nebulaAlpha.createPlayer(data[0], Double.valueOf(data[1]).doubleValue(), Double.valueOf(data[2]).doubleValue());
                }
                break;
            case GameCommand.GAME_PLAY_MOVE_PLAYER:
                content = new String(message.data);
                data = StringUtils.split(content, ";");
                if (data != null && data.length == 3) {
                    nebulaAlpha.moveOnlinePlayer(data[0], Double.valueOf(data[1]).doubleValue(), Double.valueOf(data[2]).doubleValue());
                }
                break;
            case GameCommand.GAME_PLAY_CREATE_BULLET:
                content = new String(message.data);
                data = StringUtils.split(content, ";");
                if (data != null && data.length == 3) {
                    nebulaAlpha.createBullet(data[0], Double.valueOf(data[1]).doubleValue(), Double.valueOf(data[2]).doubleValue());
                }
                break;
            default:
                break;
        }
    }

    public void disconnect() {
        responseQueue.enqueue(new DisconnectPlayerMessage(nebulaAlpha.inPlayer.getName()));
    }
}
